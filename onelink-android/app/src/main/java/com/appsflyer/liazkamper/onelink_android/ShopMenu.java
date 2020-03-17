package com.appsflyer.liazkamper.onelink_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class ShopMenu extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        view.<Button>findViewById(R.id.shoes_button).setOnClickListener(v ->
                navController.navigate(R.id.action_shop_menu2_to_shoes_section2));
        view.<Button>findViewById(R.id.jeans_button).setOnClickListener(v ->
                navController.navigate(R.id.action_shop_menu2_to_jeans_section2));
    }
}