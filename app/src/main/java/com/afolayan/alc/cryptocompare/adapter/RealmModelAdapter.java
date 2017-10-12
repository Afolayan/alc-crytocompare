package com.afolayan.alc.cryptocompare.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Afolayan Oluwaseyi on 10/11/17.
 */

public class RealmModelAdapter<T extends RealmObject> extends RealmBaseAdapter<T> {

    RealmModelAdapter(Context context, RealmResults<T> realmResults, boolean automaticUpdate) {
        super(realmResults);
        //super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;
    }
}
