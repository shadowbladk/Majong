package com.example.realmajong;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ChooseLocationCoworkingSpace extends AppCompatActivity {

    ArrayList<CoworkingSpace> csList;
    ArrayList<MaterialCardView> cardList;
    RelativeLayout locationsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_location_coworkingspace);

        String json = null;
        try {
            InputStream is = getAssets().open("coworkingSpaceData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<CoworkingSpace>>() { }.getType();

        csList = gson.fromJson(json, type);

        locationsLayout = findViewById(R.id.locationsLayout);
        cardList = new ArrayList<>();

        for(CoworkingSpace c: csList){
            MaterialCardView cardView = new MaterialCardView(this);
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(dp2px(this, 206),
                    dp2px(this, 388));

            if (cardList.size() % 2 == 1) {
                rlp.addRule(RelativeLayout.RIGHT_OF, cardList.get(cardList.size() - 1).getId());
                rlp.addRule(RelativeLayout.ALIGN_TOP, cardList.get(cardList.size() - 1).getId());
            } else if (cardList.size() != 0) {
                rlp.addRule(RelativeLayout.BELOW, cardList.get(cardList.size() - 2).getId());
            }
            cardView.setLayoutParams(rlp);
            cardView.setId(View.generateViewId());
            cardList.add(cardView);

            RelativeLayout relativeLayout = new RelativeLayout(this);
            rlp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            rlp.addRule(RelativeLayout.CENTER_HORIZONTAL);

            ImageView textBg = new ImageView(this);

            rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,dp2px(this, 85));
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            textBg.setLayoutParams(rlp);
            textBg.setId(View.generateViewId());
            textBg.setBackgroundColor(Color.BLACK);

            ImageView storefront = new ImageView(this);
            rlp = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    dp2px(this, 303));
            rlp.addRule(RelativeLayout.ABOVE, textBg.getId());
            storefront.setLayoutParams(rlp);
            Log.i("Path " , c.getPathSF());
            storefront.setBackground(getResources().getDrawable(getResources().getIdentifier("@drawable/" + c.getPathSF(), null, getPackageName())));

            rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rlp.setMargins(dp2px(this, 10), dp2px(this, 313), 0, dp2px(this, 2));
            TextView storeName = new TextView(this);
            storeName.setLayoutParams(rlp);
            storeName.setTextColor(Color.WHITE);
            storeName.setTextSize(20);
            storeName.setId(View.generateViewId());
            storeName.setText(c.getName());

            relativeLayout.addView(storefront);
            relativeLayout.addView(textBg);
            relativeLayout.addView(storeName);

            cardView.addView(relativeLayout);
            locationsLayout.addView(cardView);


            cardView.setTag(cardList.size() - 1);
            cardView.setOnClickListener(switchMenu);
        }
    }

    public View.OnClickListener switchMenu = new View.OnClickListener() {
        public void onClick(View v) {
            int index = (int) v.getTag();
            Intent j = CoworkingReserve.getIntent(ChooseLocationCoworkingSpace.this);
            j.putExtra("name", csList.get(index).getName());
            j.putExtra("address", csList.get(index).getAddress());
            j.putExtra("path", csList.get(index).getPath());
            startActivity(j);
        }
    };

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, ChooseLocationCoworkingSpace.class);
    }
}