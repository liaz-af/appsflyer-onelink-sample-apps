package com.appsflyer.liazkamper.onelink_android;

import android.app.PendingIntent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.Map;

import static com.appsflyer.liazkamper.onelink_android.AppsflyerShopApp.LOG_TAG;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppsFlyerLib.getInstance().registerConversionListener(this, new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {
                for (String attrName : conversionData.keySet())
                    Log.d(LOG_TAG, "attribute: " + attrName + " = " + conversionData.get(attrName));
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d(LOG_TAG, "error getting conversion data: " + errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> conversionData) {
                for (String attrName : conversionData.keySet())
                    Log.d(LOG_TAG, "attribute: " + attrName + " = " + conversionData.get(attrName));
                Log.d(LOG_TAG, "Deep linking into " + conversionData.get("af_sub1"));
                String targetSectionTag = conversionData.get("af_sub1") + "_section_fragment";
                int targetId = getResources().getIdentifier(targetSectionTag, "id", getPackageName());
                Bundle dp_args = new Bundle();
                dp_args.putString("item_id", conversionData.get("item_id"));
                try {
                    Navigation.findNavController(MainActivity.this, R.id.my_nav_host_fragment)
                            .createDeepLink()
                            .setGraph(R.navigation.nav_graph_deeplink)
                            .setDestination(targetId)
                            .setArguments(dp_args)
                            .createPendingIntent()
                            .send();
                    finish();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d(LOG_TAG, "error onAttributionFailure : " + errorMessage);
            }
        });
    }
}