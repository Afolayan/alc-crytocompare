package com.afolayan.alc.cryptocompare.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.afolayan.alc.cryptocompare.R;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Afolayan Oluwaseyi on 10/6/17.
 */

public class Currency {

    public static final String TAG = Currency.class.getSimpleName();
    @SerializedName("symbol")
    private String symbol;
    @SerializedName("name")
    private String name;
    @SerializedName("code")
    private String code;

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return ellipsize(name, 25); //add ellipsis after 25 characters
    }

    public String getCode() {
        return code;
    }

    public List<Currency> getCurrencies(Context context){
        InputStream is = context.getResources().openRawResource(R.raw.currencies);
        Gson gson = new Gson();
        try {
            JsonReader reader = gson.newJsonReader(new InputStreamReader(is, "UTF-8"));
            return gson.fromJson( reader, new TypeToken<List<Currency>>(){}.getType());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String ellipsize(String input, int maxLength) {
        if (input == null || input.length() < maxLength) {
            return input;
        }
        return input.substring(0, maxLength) + "...";
    }
}