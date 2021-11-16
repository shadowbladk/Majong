package com.example.realmajong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class RestaurantMenu extends AppCompatActivity {

    RelativeLayout parentLayout;
    ArrayList<RestaurantData> restaurantList;
    ArrayList<MaterialCardView> cardListRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);

        parentLayout = findViewById(R.id.layout);

        String json = null;
        try {
            InputStream is = getAssets().open("restaurantData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<RestaurantData>>() { }.getType();

        restaurantList = gson.fromJson(json, type);
        cardListRestaurant = new ArrayList<>();

        for (RestaurantData res: restaurantList) {
            MaterialCardView cardAll = new MaterialCardView(this);
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, Utility.dp2px(this, 150));
            if (res == restaurantList.get(restaurantList.size() - 1))
                rlp.setMargins(Utility.dp2px(this, 15), Utility.dp2px(this, 15), Utility.dp2px(this, 15), Utility.dp2px(this, 15));
            else
                rlp.setMargins(Utility.dp2px(this, 15), Utility.dp2px(this, 15), Utility.dp2px(this, 15), 0);

            if (cardListRestaurant.size() != 0)
                rlp.addRule(RelativeLayout.BELOW, cardListRestaurant.get(cardListRestaurant.size() - 1).getId());
            else
                rlp.addRule(RelativeLayout.BELOW, findViewById(R.id.textRestaurants).getId());

            cardAll.setLayoutParams(rlp);
            cardAll.setId(View.generateViewId());
            cardAll.setRadius(Utility.dp2px(this, 10));
            cardAll.setTag(cardListRestaurant.size());
            cardAll.setOnClickListener(switchMenu);
            cardAll.setCardBackgroundColor(Color.WHITE);

            RelativeLayout relativeLayoutAll = new RelativeLayout(this);
            relativeLayoutAll.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

            ImageView banner = new ImageView(this);
            rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, Utility.dp2px(this, 90));
            banner.setLayoutParams(rlp);
            banner.setScaleType(ImageView.ScaleType.CENTER_CROP);
            banner.setImageDrawable(ResourcesCompat.getDrawable(getResources(), getResources().getIdentifier("@drawable/" + res.getBannerPath(), null, getPackageName()), null));



            MaterialCardView cardBackground = new MaterialCardView(this);
            rlp = new RelativeLayout.LayoutParams(Utility.dp2px(this, 62), Utility.dp2px(this, 62));
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            rlp.setMargins(Utility.dp2px(this, 10), Utility.dp2px(this, 10), Utility.dp2px(this, 10), Utility.dp2px(this, 10));
            cardBackground.setLayoutParams(rlp);
            cardBackground.setId(View.generateViewId());
            cardBackground.setCardBackgroundColor(Color.BLACK);
            cardBackground.setRadius(Utility.dp2px(this, 5));

            RelativeLayout relativeLayoutIcon = new RelativeLayout(this);
            relativeLayoutIcon.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

            MaterialCardView cardIcon = new MaterialCardView(this);
            rlp = new RelativeLayout.LayoutParams(Utility.dp2px(this, 60), Utility.dp2px(this, 60));
            rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
            cardIcon.setLayoutParams(rlp);
            cardIcon.setRadius(Utility.dp2px(this, 5));

            ImageView icon = new ImageView(this);
            icon.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            icon.setBackgroundColor(Color.WHITE);
            //TODO
            icon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), getResources().getIdentifier("@drawable/" + res.getLogoPath(), null, getPackageName()), null));

            cardIcon.addView(icon);
            relativeLayoutIcon.addView(cardIcon);
            cardBackground.addView(relativeLayoutIcon);
            relativeLayoutAll.addView(banner);
            relativeLayoutAll.addView(cardBackground);

            ImageView pin = new ImageView(this);
            rlp = new RelativeLayout.LayoutParams(Utility.dp2px(this, 15), Utility.dp2px(this, 15));
            rlp.addRule(RelativeLayout.ALIGN_BOTTOM, cardBackground.getId());
            rlp.addRule(RelativeLayout.END_OF, cardBackground.getId());
            rlp.setMargins(Utility.dp2px(this, 5), 0, 0, 0);
            pin.setLayoutParams(rlp);
            pin.setId(View.generateViewId());
            pin.setImageDrawable(ResourcesCompat.getDrawable(getResources(), getResources().getIdentifier("@drawable/location_pin", null, getPackageName()), null));

            TextView restaurantName = new TextView(this);
            rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rlp.addRule(RelativeLayout.ABOVE, pin.getId());
            rlp.addRule(RelativeLayout.ALIGN_START, pin.getId());
            rlp.setMargins(0, 0, 0, Utility.dp2px(this, 5));
            restaurantName.setLayoutParams(rlp);
            restaurantName.setText(res.getName());
            restaurantName.setTextColor(Color.BLACK);
            restaurantName.setTextSize(18);
            restaurantName.setTypeface(null, Typeface.BOLD);

            TextView restaurantLocation = new TextView(this);
            rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rlp.addRule(RelativeLayout.END_OF, pin.getId());
            rlp.addRule(RelativeLayout.ALIGN_BOTTOM, pin.getId());
            rlp.setMargins(Utility.dp2px(this, 5), 0, 0, 0);
            restaurantLocation.setLayoutParams(rlp);
            restaurantLocation.setTextColor(Color.BLACK);
            restaurantLocation.setTextSize(12);
            restaurantLocation.setText(res.getLocation());

            relativeLayoutAll.addView(restaurantLocation);
            relativeLayoutAll.addView(restaurantName);
            relativeLayoutAll.addView(pin);
            cardAll.addView(relativeLayoutAll);
            parentLayout.addView(cardAll);
            cardListRestaurant.add(cardAll);
        }
    }

    public View.OnClickListener switchMenu = new View.OnClickListener() {
        //TODO
        public void onClick(View v) {
            int index = (int) v.getTag();
            Intent i = RestaurantMenu2.getIntent(RestaurantMenu.this);
            i.putExtra("name", restaurantList.get(index).getName());
            i.putExtra("location", restaurantList.get(index).getLocation());
            i.putExtra("logoPath", restaurantList.get(index).getLogoPath());
            i.putExtra("bannerPath", restaurantList.get(index).getBannerPath());

            startActivity(i);
        }
    };


    public static Intent getIntent(Context context) {
        return new Intent(context, RestaurantMenu.class);
    }
}