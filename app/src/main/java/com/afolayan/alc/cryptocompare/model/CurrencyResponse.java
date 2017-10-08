package com.afolayan.alc.cryptocompare.model;

/**
 * Created by Afolayan Oluwaseyi on 10/8/17.
 */

public class CurrencyResponse {
    /**
     * URL = https://min-api.cryptocompare.com/data/pricemultifull?fsyms=ETH,DASH&tsyms=BTC,USD,EUR
     * {
     "RAW":{
         "BTC":{
             "USD":{
             "TYPE":"5",
             "MARKET":"CCCAGG",
             "FROMSYMBOL":"BTC",
             "TOSYMBOL":"USD",
             "FLAGS":"2",
             "PRICE":1082.13,
             "LASTUPDATE":1483529467,
             "LASTVOLUME":2.31159402,
             "LASTVOLUMETO":2496.5215415999996,
             "LASTTRADEID":12826318,
             "VOLUME24HOUR":72040.63471484324,
             "VOLUME24HOURTO":75043516.07861365,
             "OPEN24HOUR":1020.95,
             "HIGH24HOUR":1097.54,
             "LOW24HOUR":980,
             "LASTMARKET":"Bitstamp",
             "CHANGE24HOUR":61.180000000000064,
             "CHANGEPCT24HOUR":5.992458004799457
            },
     ...
     },
     ...
     "DISPLAY":{  ...}
     }

     */
    public CurrencyResponse(String currencyCode){

    }

    public String getMe(){
        return "";
    }
}
