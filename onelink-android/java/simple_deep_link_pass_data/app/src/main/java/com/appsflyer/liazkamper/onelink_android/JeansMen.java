package com.appsflyer.liazkamper.onelink_android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.appsflyer.liazkamper.onelink_android.AppsflyerShopApp.LOG_TAG;

public class JeansMen extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jeans_men, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView jeansImage = view.findViewById(R.id.men_jeans_image);
        view.<Button>findViewById(R.id.men_black_button)
                .setOnClickListener(v -> jeansImage.setImageResource(R.drawable.black_jeans_men));
        view.<Button>findViewById(R.id.men_blue_button)
                .setOnClickListener(v -> jeansImage.setImageResource(R.drawable.blue_jeans_men));
        view.<Button>findViewById(R.id.men_sale_button)
                .setOnClickListener(v -> jeansImage.setImageResource(R.drawable.jeans_men_sale));
        if (getArguments() != null) {
            Log.d(LOG_TAG, "Deep link into Men Jeans");
            switch (getArguments().getString("item_id")) {
                case "black":
                    jeansImage.setImageResource(R.drawable.black_jeans_men);
                    break;
                case "blue":
                    jeansImage.setImageResource(R.drawable.blue_jeans_men);
                    break;
                case "sale":
                    jeansImage.setImageResource(R.drawable.jeans_men_sale);
                    break;
            }
        }
    }
}