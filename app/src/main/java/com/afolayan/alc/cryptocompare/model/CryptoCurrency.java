package com.afolayan.alc.cryptocompare.model;

import io.realm.RealmObject;

/**
 * Created by Afolayan Oluwaseyi on 10/8/17.
 */

public class CryptoCurrency extends RealmObject{

    private String market;
    private String lastmarket;

    private String type;
    private String fromSymbol; //ETH or BTC
    private String toSymbol;  //NGN, USD, etc
    private String fromSymbolIcon;
    private String toSymbolIcon;
    private String price;
    private long lastUpdate;

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

    public String getFromSymbolIcon() {
        return fromSymbolIcon;
    }

    public void setFromSymbolIcon(String fromSymbolIcon) {
        this.fromSymbolIcon = fromSymbolIcon;
    }

    public String getToSymbolIcon() {
        return toSymbolIcon;
    }

    public void setToSymbolIcon(String toSymbolIcon) {
        this.toSymbolIcon = toSymbolIcon;
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
        cryptoCurrency.setMarket(thisCurrency.getMarket());
        return cryptoCurrency;
    }
}
