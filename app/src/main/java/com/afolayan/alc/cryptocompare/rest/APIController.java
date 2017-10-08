package com.afolayan.alc.cryptocompare.rest;

import android.util.Log;

import com.afolayan.alc.cryptocompare.model.CurrencyResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by Afolayan Oluwaseyi on 10/8/17.
 */

public class APIController implements Callback<String> {
    static final String BASE_URL = "https://min-api.cryptocompare.com/data/";
    public static final String TAG = APIController.class.getSimpleName();

    public void start(String currencyCode){
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

        Call<String> call = apiInterface.getCurentDetails(data);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        Log.e(TAG, "onResponse: "+response.body());
        Log.e(TAG, "onResponse url== : "+response.raw().request().url());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Type mapType = new TypeToken<Map<String, CurrencyResponse> >() {}.getType(); // define generic type
        Map<String, CurrencyResponse> result= gson.fromJson( response.body(), mapType);
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        t.printStackTrace();
    }
}
