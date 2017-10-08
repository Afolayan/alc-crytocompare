package com.afolayan.alc.cryptocompare.rest;

import com.afolayan.alc.cryptocompare.model.CurrencyResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Afolayan Oluwaseyi on 10/8/17.
 */

public interface APIInterface {

    @GET("pricemultifull")
    Call<String> getCurentDetails(@QueryMap Map<String, String> queries);

}
