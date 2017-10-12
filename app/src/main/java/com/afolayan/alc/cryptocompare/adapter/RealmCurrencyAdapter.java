package com.afolayan.alc.cryptocompare.adapter;

import android.content.Context;

import com.afolayan.alc.cryptocompare.model.CryptoCurrency;

import io.realm.RealmResults;

/**
 * Created by Afolayan Oluwaseyi on 10/11/17.
 */

public class RealmCurrencyAdapter extends RealmModelAdapter<CryptoCurrency> {
    public RealmCurrencyAdapter(Context context, RealmResults<CryptoCurrency> realmResults,
                                boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
