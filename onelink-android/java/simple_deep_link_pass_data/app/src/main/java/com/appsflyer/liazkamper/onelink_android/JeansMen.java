package com.appsflyer.liazkamper.onelink_android;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appsflyer.CreateOneLinkHttpTask;
import com.appsflyer.share.ShareInviteHelper;
import com.appsflyer.share.LinkGenerator;

import static com.appsflyer.liazkamper.onelink_android.AppsflyerShopApp.LOG_TAG;

public class JeansMen extends Fragment {

    private String currentChoice = "sale";
    private final String username = "TheKing";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jeans_men, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView usernameText = view.findViewById(R.id.usernameText);
        usernameText.setText(username);

        ImageView jeansImage = view.findViewById(R.id.men_jeans_image);
        view.<Button>findViewById(R.id.men_black_button)
                .setOnClickListener(v -> {
                    jeansImage.setImageResource(R.drawable.black_jeans_men);
                    currentChoice = "black";
                });
        view.<Button>findViewById(R.id.men_blue_button)
                .setOnClickListener(v -> {
                    jeansImage.setImageResource(R.drawable.blue_jeans_men);
                    currentChoice = "blue";
                });
        view.<Button>findViewById(R.id.men_sale_button)
                .setOnClickListener(v -> {
                    jeansImage.setImageResource(R.drawable.jeans_men_sale);
                    currentChoice = "sale";
                });
        view.<Button>findViewById(R.id.share_button)
                .setOnClickListener(v -> inviteFriend());
        if (getArguments() != null) {
            Log.d(LOG_TAG, "Deep link into Men Jeans");
            // Check if this deep link originiated in a user invite
            if (getArguments().getString("referrer_name") != "") {
                Toast.makeText(getActivity(),"You were invited by " + getArguments().getString("referrer_name"),
                               Toast.LENGTH_SHORT).show();
            }
            switch (getArguments().getString("item_id")) {
                case "black":
                    jeansImage.setImageResource(R.drawable.black_jeans_men);
                    currentChoice = "black";
                    break;
                case "blue":
                    jeansImage.setImageResource(R.drawable.blue_jeans_men);
                    currentChoice = "blue";
                    break;
                case "sale":
                    jeansImage.setImageResource(R.drawable.jeans_men_sale);
                    currentChoice = "sale";
                    break;
            }
        }
    }

    private void inviteFriend() {
        LinkGenerator linkGenerator = ShareInviteHelper.generateInviteUrl(getActivity());
        linkGenerator.setChannel("SMS");
        linkGenerator.setReferrerName(username);
        linkGenerator.addParameter("af_sub1", "jeans_men");
        linkGenerator.addParameter("item_id", currentChoice);
        CreateOneLinkHttpTask.ResponseListener listener = new CreateOneLinkHttpTask.ResponseListener() {
            @Override
            public void onResponse(String s) {
                Log.d("Invite Link", s);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Copied", s);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(getActivity(),"Shared link copied to clipboard",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponseError(String s) {
                // handle response error
            }
        };
        linkGenerator.generateLink(getActivity(), listener);
    }
}