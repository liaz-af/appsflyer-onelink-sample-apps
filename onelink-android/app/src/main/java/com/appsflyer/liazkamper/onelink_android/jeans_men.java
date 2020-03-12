package com.appsflyer.liazkamper.onelink_android;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class jeans_men extends Fragment implements View.OnClickListener {

    private ImageView jeansImage;

    public jeans_men() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_jeans_men, container, false);
        jeansImage = rootView.findViewById(R.id.men_jeans_image);
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button blackBtn = view.findViewById(R.id.men_black_button);
        Button blueBtn = view.findViewById(R.id.men_blue_button);
        Button saleBtn = view.findViewById(R.id.men_sale_button);

        blackBtn.setOnClickListener(this);
        blueBtn.setOnClickListener(this);
        saleBtn.setOnClickListener(this);

        if (getArguments() != null) {
            String itemId = requireArguments().getString("item_id");
                Log.d("AppsFlyer_LOG_TAG", "Deep link into Men Jeans");

            switch (itemId) {
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.men_black_button:
                jeansImage.setImageResource(R.drawable.black_jeans_men);
                break;
            case R.id.men_blue_button:
                jeansImage.setImageResource(R.drawable.blue_jeans_men);
                break;
            case R.id.men_sale_button:
                jeansImage.setImageResource(R.drawable.jeans_men_sale);
                break;
        }
    }
}
