package com.appsflyer.liazkamper.appsflyeronelinkbasicapp;

import android.app.Application;

import com.appsflyer.AppsFlyerLib;

public class AppsflyerBasicApp extends Application {
    public static final String LOG_TAG = "AppsflyerBasicApp";
    @Override
    public void onCreate() {
        super.onCreate();
        //noinspection SpellCheckingInspection
        String afDevKey = "dQnXTbxz7UyXL7sy2rvgx";
        AppsFlyerLib appsflyer = AppsFlyerLib.getInstance();
        appsflyer.setMinTimeBetweenSessions(0);
        appsflyer.init(afDevKey, null, this);
        appsflyer.startTracking(this, afDevKey);
        appsflyer.setDebugLog(true);
    }
}