package com.afolayan.alc.cryptocompare.model;


import android.content.Context;

import com.afolayan.alc.cryptocompare.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Afolayan Oluwaseyi on 10/8/17.
 */

public class CryptoCurrency extends RealmObject{

    private String market;
    private String lastmarket;

    String type;
    String fromSymbol; //ETH or BTC
    String toSymbol;  //NGN, USD, etc
    String price;
    long lastUpdate;

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getLastmarket() {
        return lastmarket;
    }

    public void setLastmarket(String lastmarket) {
        this.lastmarket = lastmarket;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFromSymbol() {
        return fromSymbol;
    }

    public void setFromSymbol(String fromSymbol) {
        this.fromSymbol = fromSymbol;
    }

    public String getToSymbol() {
        return toSymbol;
    }

    public void setToSymbol(String toSymbol) {
        this.toSymbol = toSymbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return  "FROM: "+getFromSymbol()
                    +"\nTO: "+getToSymbol()
                    +"\nTYPE: "+getType()
                    +"\nPRICE: "+getPrice()
                    +"\nLAST UPDATE: "+getLastUpdate()
                    +"\nLAST MARKET: "+getLastmarket();

    }
    public static CryptoCurrency makeCryptoObject(CurrencyResponse thisCurrency) {
        CryptoCurrency cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setFromSymbol(thisCurrency.getFromSymbol());
        cryptoCurrency.setToSymbol(thisCurrency.getToSymbol());
        cryptoCurrency.setType(thisCurrency.getType());
        cryptoCurrency.setPrice(thisCurrency.getPrice());
        cryptoCurrency.setLastUpdate(thisCurrency.getLastUpdate());
        cryptoCurrency.setLastmarket(thisCurrency.getLastMarket());
        return cryptoCurrency;
    }
}
