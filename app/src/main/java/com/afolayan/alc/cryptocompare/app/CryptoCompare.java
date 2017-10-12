package com.afolayan.alc.cryptocompare.app;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.afolayan.alc.cryptocompare.db.Migration;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class CryptoCompare extends MultiDexApplication {
    public CryptoCompare() {
        super();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("crypto-compare.realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .migration(new Migration())
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
