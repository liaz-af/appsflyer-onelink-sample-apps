package com.appsflyer.liazkamper.onelink_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.PendingIntent;
import android.os.Bundle;
import android.util.Log;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String AF_DEV_KEY = "dQnXTbxz7UyXL7sy2rvgx";;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Set Up Conversion Listener to get attribution data **/
        final NavController navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);

        AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {


            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {

                for (String attrName : conversionData.keySet()) {
                    Log.d("AppsFlyer_LOG_TAG", "attribute: " + attrName + " = " + conversionData.get(attrName));
                }
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d("AppsFlyer_LOG_TAG", "error getting conversion data: " + errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> conversionData) {

                for (String attrName : conversionData.keySet()) {
                    Log.d("AppsFlyer_LOG_TAG", "attribute: " + attrName + " = " + conversionData.get(attrName));
                }

                Log.d("AppsFlyer_LOG_TAG","Deep linking into " + conversionData.get("af_sub1"));
                String targetSectionTag = conversionData.get("af_sub1") + "_section_fragment";
                int targetId = getResources().getIdentifier(targetSectionTag, "id", getPackageName());

                Bundle dp_args = new Bundle();
                dp_args.putString("item_id", conversionData.get("item_id"));

                PendingIntent pendingIntent = navController.createDeepLink().
                                                setGraph(R.navigation.nav_graph_deeplink).
                                                setDestination(targetId).
                                                setArguments(dp_args).
                                                createPendingIntent();
                try {
                    pendingIntent.send();
                    finish();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d("AppsFlyer_LOG_TAG", "error onAttributionFailure : " + errorMessage);
            }
        };


        AppsFlyerLib.getInstance().init(AF_DEV_KEY, conversionListener, this);
    }
}
