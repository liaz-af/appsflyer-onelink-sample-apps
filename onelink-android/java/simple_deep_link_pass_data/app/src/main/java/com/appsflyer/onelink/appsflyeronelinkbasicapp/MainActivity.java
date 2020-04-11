package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.appsflyer.onelink.appsflyeronelinkbasicapp.AppsflyerBasicApp.LOG_TAG;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppsFlyerLib.getInstance().registerConversionListener(this, new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {
                for (String attrName : conversionData.keySet())
                    Log.d(LOG_TAG, "Conversion attribute: " + attrName + " = " + conversionData.get(attrName));
                //TODO - remove this
                String status = Objects.requireNonNull(conversionData.get("af_status")).toString();
                if(status.equals("Non-organic")){
                    if( Objects.requireNonNull(conversionData.get("is_first_launch")).toString().equals("true")){
                        Log.d(LOG_TAG,"Conversion: First Launch");
                        if (conversionData.containsKey("item_id")){
                            Log.d(LOG_TAG,"Conversion: This is deferred deep linking.");
                            // TODO - match the input types
                            Map<String,String> newMap = new HashMap<>();
                            for (Map.Entry<String, Object> entry : conversionData.entrySet()) {
                                if(entry.getValue() instanceof String){
                                    newMap.put(entry.getKey(), (String) entry.getValue());
                                }
                            }
                            this.onAppOpenAttribution(newMap);
                        }
                    } else {
                        Log.d(LOG_TAG,"Conversion: Not First Launch");
                    }
                } else {
                    Log.d(LOG_TAG,"Conversion: This is an organic install.");
                }
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d(LOG_TAG, "error getting conversion data: " + errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> conversionData) {
                for (String attrName : conversionData.keySet())
                    Log.d(LOG_TAG, "Deeplink attribute: " + attrName + " = " + conversionData.get(attrName));
                Log.d(LOG_TAG, "Deep linking into " + conversionData.get("section_id"));
                String targetSectionTag = conversionData.get("section_id") + "_section_fragment";
                int targetId = getResources().getIdentifier(targetSectionTag, "id", getPackageName());
                Bundle dp_args = new Bundle();
                dp_args.putString("item_id", conversionData.get("item_id"));
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d(LOG_TAG, "error onAttributionFailure : " + errorMessage);
            }
        });
    }

    public void goToApples(View view) {
        Intent intent = new Intent(this, applesActivity.class);
        startActivity(intent);
    }

    public void goToBananas(View view) {
        Intent intent = new Intent(this, bananasActivity.class);
        startActivity(intent);
    }

    public void goToPeaches(View view) {
        Intent intent = new Intent(this, peachesActivity.class);
        startActivity(intent);
    }
}