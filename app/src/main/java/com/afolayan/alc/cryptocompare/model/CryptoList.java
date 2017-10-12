package com.afolayan.alc.cryptocompare.model;

import java.util.HashMap;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Afolayan Oluwaseyi on 10/11/17.
 */

public class CryptoList extends RealmObject{

    @Required @PrimaryKey String ID = UUID.randomUUID().toString();

    RealmList< CryptoCurrency > cryptoCurrencies;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public RealmList<CryptoCurrency> getCryptoCurrencies() {
        return cryptoCurrencies;
    }

    public void setCryptoCurrencies(RealmList<CryptoCurrency> cryptoCurrencies) {
        this.cryptoCurrencies = cryptoCurrencies;
    }
}