package com.afolayan.alc.cryptocompare.rest;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import com.afolayan.alc.cryptocompare.model.CryptoCurrency;
import com.afolayan.alc.cryptocompare.model.CryptoList;
import com.afolayan.alc.cryptocompare.model.CurrencyResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String jsonResponse = response.body();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        String ethCurrencyObject = "";
        String btcCurrencyObject = "";
        try {
            JSONObject object = new JSONObject(jsonResponse);

            JSONObject ethJsonObject = object.getJSONObject("RAW")
                                    .getJSONObject("ETH")
                                    .getJSONObject(currencyCode);
            JSONObject btcJsonObject = object.getJSONObject("RAW")
                                    .getJSONObject("BTC")
                                    .getJSONObject(currencyCode);
            ethCurrencyObject = ethJsonObject.toString();
            btcCurrencyObject = btcJsonObject.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RealmList<CryptoCurrency> value = new RealmList<>();

        if(!TextUtils.isEmpty(ethCurrencyObject)) {
            CurrencyResponse thisCurrency = CurrencyResponse.getCurrencyDetails(ethCurrencyObject);
            value.add( makeCryptoObject(thisCurrency) );
        }
        if(!TextUtils.isEmpty(btcCurrencyObject)) {
            CurrencyResponse thisCurrency = CurrencyResponse.getCurrencyDetails(btcCurrencyObject);
            value.add( makeCryptoObject(thisCurrency) );
        }

        saveCurrency(value);

        if( alertDialog != null ){
            alertDialog.dismiss();
        }
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
               Toast.makeText( alertDialog.getContext(), "Successfully Added Card", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(alertDialog.getContext(), "Cannot add card. Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private static void saveCurrencies(final RealmList<CryptoCurrency> currency) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CryptoList list = new CryptoList();
                list.setCryptoCurrencies( currency );
                realm.insertOrUpdate(list);
            }
        });
    }
}
