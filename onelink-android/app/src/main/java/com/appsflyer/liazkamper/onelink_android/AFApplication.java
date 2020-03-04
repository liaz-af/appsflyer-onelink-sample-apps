package com.appsflyer.liazkamper.onelink_android;

import android.app.Application;
import android.util.Log;

import com.appsflyer.AFLogger;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AppsFlyerConversionListener;
import java.util.Map;

public class AFApplication extends Application {
    private static final String AF_DEV_KEY = "dQnXTbxz7UyXL7sy2rvgx";

    @Override
    public void onCreate() {
        super.onCreate();

        AppsFlyerLib.getInstance().startTracking(this, AF_DEV_KEY);

        /* Set to true to see the debug logs. Comment out or set to false to stop the function */
        AppsFlyerLib.getInstance().setDebugLog(true);


    }
}
