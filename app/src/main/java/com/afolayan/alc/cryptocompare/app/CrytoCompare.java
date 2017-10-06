package com.afolayan.alc.cryptocompare.app;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


public class CrytoCompare extends MultiDexApplication {
    public CrytoCompare() {
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
    }
}
