package com.afolayan.alc.cryptocompare.db;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.afolayan.alc.cryptocompare.model.CryptoCurrency;
import com.afolayan.alc.cryptocompare.model.Currency;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Afolayan Oluwaseyi on 10/11/17.
 */

public class CurrencyRealmController extends RealmController{

    private final Realm realm;

    public CurrencyRealmController(Application application) {
        super(application);
        realm = Realm.getDefaultInstance();
        with(application);
    }

    //clear all objects from CryptoCurrency.class
    public void clearAll() {
        realm.beginTransaction();
        realm.delete(CryptoCurrency.class);
        realm.commitTransaction();
    }

    //find all objects in the CryptoCurrency.class
    public RealmResults<CryptoCurrency> getCryptoCurrencies() {
        return realm.where(CryptoCurrency.class).findAllAsync();
    }
    //query a single item with the given id
    public CryptoCurrency getCryptoCurrency(String id) {

        return realm.where(CryptoCurrency.class).equalTo("id", id).findFirst();
    }

    //check if CryptoCurrency.class is empty
    public boolean hasCryptoCurrencys() {
        return true;
        //return !realm.allObjects(CryptoCurrency.class).isEmpty();
    }

    //query example
    public RealmResults<CryptoCurrency> queryedCryptoCurrencies() {

        return realm.where(CryptoCurrency.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
    public void changeCurrencyName(final String CryptoCurrencyId, final String name) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CryptoCurrency cryptoCurrency = realm.where(CryptoCurrency.class)
                        .equalTo("id", CryptoCurrencyId)
                        .findFirst();
                cryptoCurrency.setToSymbol(name);
            }
        });
    }





    public void deleteCryptoCurrency(final String CryptoCurrencyId) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(CryptoCurrency.class).equalTo("id", CryptoCurrencyId)
                        .findFirst()
                        .deleteFromRealm();
            }
        });
    }

    public void deleteAll() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(CryptoCurrency.class)
                        .findAll()
                        .deleteAllFromRealm();
            }
        });
    }
}
