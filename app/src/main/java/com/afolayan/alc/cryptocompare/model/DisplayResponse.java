package com.afolayan.alc.cryptocompare.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Afolayan Oluwaseyi on 10/21/17.
 */

public class DisplayResponse {

    public DisplayResponse(){
    }

    @SerializedName("MARKET") String market;
    @SerializedName("FROMSYMBOL") String fromSymbol;
    @SerializedName("TOSYMBOL") String toSymbol;
    @SerializedName("PRICE") String price;
    @SerializedName("LASTMARKET") String lastMarket;
    @SerializedName("LASTUPDATE") String lastUpdate;


    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
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

    public String getLastMarket() {
        return lastMarket;
    }

    public void setLastMarket(String lastMarket) {
        this.lastMarket = lastMarket;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public static DisplayResponse getCurrencyDetails(String currencyJson){
        Gson gson = new Gson();
        return gson.fromJson( currencyJson, DisplayResponse.class);
    }
}
