package com.afolayan.alc.cryptocompare.rest;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.afolayan.alc.cryptocompare.model.CryptoCurrency;
import com.afolayan.alc.cryptocompare.model.CryptoList;
import com.afolayan.alc.cryptocompare.model.CurrencyResponse;
import com.afolayan.alc.cryptocompare.model.DisplayResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.afolayan.alc.cryptocompare.model.CryptoCurrency.makeCryptoObject;


/**
 * Created by Afolayan Oluwaseyi on 10/8/17.
 */

public class APIController implements Callback<String> {
    static final String BASE_URL = "https://min-api.cryptocompare.com/data/";
    public static final String TAG = APIController.class.getSimpleName();

    AlertDialog alertDialog;
    String currencyCode;

    public void start(String currencyCode, AlertDialog alertDialog){
        this.alertDialog  = alertDialog;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);
        Map<String, String> data = new HashMap<>();
        data.put("fsyms", "ETH,BTC");
        data.put("tsyms", currencyCode);
        this.currencyCode = currencyCode;

        Call<String> call = apiInterface.getCurrencyDetails(data);
        call.enqueue(this);

    }

    public void start(final List<String> currencyCodes, final List<String> idList,
                      final SwipeRefreshLayout mSwipeRefreshLayout){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);
        Map<String, String> data = new HashMap<>();
        data.put("fsyms", "ETH,BTC");

        StringBuilder builder = new StringBuilder();
        for (String code :currencyCodes) {
            builder.append(code);
            builder.append(",");
        }

        String tsyms = builder.toString().substring(0, (builder.toString().length() - 1));
        data.put("tsyms", tsyms);

        Call<String> call = apiInterface.getCurrencyDetails(data);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                getMultipleCurrencies(response, currencyCodes, idList);

                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "call: response==>"+t.getMessage());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void getMultipleCurrencies(Response<String> response, List<String> currencyCodes,
                                       List<String> idList) {

        for (int i = 0; i < idList.size(); i++) {
            String currencyCode = currencyCodes.get(i);
            RealmList<CryptoCurrency> value = getCryptoCurrencies(response.body(), currencyCode);
            updateCurrency(value, idList.get(i));
        }

    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String jsonResponse = response.body();

        RealmList<CryptoCurrency> value = getCryptoCurrencies(jsonResponse, currencyCode);

        saveCurrency(value);

        if( alertDialog != null ){
            alertDialog.dismiss();
        }
    }

    @NonNull
    private RealmList<CryptoCurrency> getCryptoCurrencies(String jsonResponse, String currencyCode) {
        String ethCurrencyObject = "";
        String btcCurrencyObject = "";

        String ethDisplayObject = "";
        String btcDisplayObject = "";
        try {
            JSONObject object = new JSONObject(jsonResponse);

            JSONObject ethJsonObject = object.getJSONObject("RAW")
                                    .getJSONObject("ETH")
                                    .getJSONObject(currencyCode);
            JSONObject btcJsonObject = object.getJSONObject("RAW")
                                    .getJSONObject("BTC")
                                    .getJSONObject(currencyCode);

            btcDisplayObject = object.getJSONObject("DISPLAY")
                                    .getJSONObject("BTC")
                                    .getJSONObject(currencyCode)
                                    .toString();
            ethDisplayObject = object.getJSONObject("DISPLAY")
                                    .getJSONObject("ETH")
                                    .getJSONObject(currencyCode)
                                    .toString();


            ethCurrencyObject = ethJsonObject.toString();
            btcCurrencyObject = btcJsonObject.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RealmList<CryptoCurrency> value = new RealmList<>();

        if(!TextUtils.isEmpty(ethCurrencyObject)) {
            CurrencyResponse thisCurrency = CurrencyResponse.getCurrencyDetails(ethCurrencyObject);
            DisplayResponse displayResponse = DisplayResponse.getCurrencyDetails(ethDisplayObject);
            CryptoCurrency cryptoCurrency = makeCryptoObject(thisCurrency);
            cryptoCurrency.setFromSymbolIcon( displayResponse.getFromSymbol() );
            cryptoCurrency.setToSymbolIcon( displayResponse.getToSymbol() );

            value.add(cryptoCurrency);
        }
        if(!TextUtils.isEmpty(btcCurrencyObject)) {
            CurrencyResponse thisCurrency = CurrencyResponse.getCurrencyDetails(btcCurrencyObject);
            DisplayResponse displayResponse = DisplayResponse.getCurrencyDetails(btcDisplayObject);
            CryptoCurrency cryptoCurrency = makeCryptoObject(thisCurrency);
            cryptoCurrency.setFromSymbolIcon( displayResponse.getFromSymbol() );
            cryptoCurrency.setToSymbolIcon( displayResponse.getToSymbol() );

            value.add(cryptoCurrency);
        }
        return value;
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        t.printStackTrace();
        if( alertDialog != null ){
            alertDialog.dismiss();
        }
    }

    private void saveCurrency(final RealmList<CryptoCurrency> currency) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CryptoList list = new CryptoList();
                list.setCryptoCurrencies( currency );
                realm.insertOrUpdate(list);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if( alertDialog != null)
                    Toast.makeText( alertDialog.getContext(), "Successfully Added Card", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if( alertDialog != null)
                    Toast.makeText(alertDialog.getContext(), "Cannot add card. Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCurrency(final RealmList<CryptoCurrency> currency, final String idToUpdate) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CryptoList list = realm.where(CryptoList.class).equalTo("ID", idToUpdate).findFirst();
                list.setCryptoCurrencies( currency );
                realm.insertOrUpdate(list);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if( alertDialog != null)
                    Toast.makeText( alertDialog.getContext(), "Successfully Updated Card", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if( alertDialog != null)
                    Toast.makeText(alertDialog.getContext(), "Cannot update card. Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
