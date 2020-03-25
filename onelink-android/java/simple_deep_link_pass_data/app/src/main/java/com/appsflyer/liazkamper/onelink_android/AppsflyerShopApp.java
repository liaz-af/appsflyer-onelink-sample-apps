package com.appsflyer.liazkamper.onelink_android;

import android.app.Application;

import com.appsflyer.AppsFlyerLib;

public class AppsflyerShopApp extends Application {
    public static final String LOG_TAG = "AppsflyerShop";
    @Override
    public void onCreate() {
        super.onCreate();
        //noinspection SpellCheckingInspection
        String afDevKey = "dQnXTbxz7UyXL7sy2rvgx";
        String afOnelinkCode = "B7QX";
        AppsFlyerLib appsflyer = AppsFlyerLib.getInstance();
        appsflyer.setMinTimeBetweenSessions(0);
        appsflyer.init(afDevKey, null, this);
        appsflyer.startTracking(this, afDevKey);
        appsflyer.setDebugLog(true);
        //Set the Onelink code to be used in user invites
        AppsFlyerLib.getInstance().setAppInviteOneLink(afOnelinkCode);
    }
}