package com.example.realmajong;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class RestaurantMenu2 extends AppCompatActivity implements View.OnClickListener {

    RestaurantData resData;
    TextView storeName, storeAddress;
    ImageView storeIcon, choice1, choice2, choice3, choice4;
    Button reserveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu2);

        storeIcon = findViewById(R.id.storeIcon);
        storeName = findViewById(R.id.storeName);
        storeAddress = findViewById(R.id.storeAddress);

        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        choice3 = findViewById(R.id.choice3);
        choice4 = findViewById(R.id.choice4);
        reserveButton = findViewById(R.id.reserveButton);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            resData = new RestaurantData(extra.getString("name"), extra.getString("location"), extra.getString("logoPath"), extra.getString("bannerPath"));
        }

        storeIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), getResources().getIdentifier("@drawable/" + resData.getLogoPath(), null, getPackageName()), null));
        storeName.setText(resData.getName());
        storeAddress.setText(resData.getLocation());

        choice1.setOnClickListener(this);
        choice2.setOnClickListener(this);
        choice3.setOnClickListener(this);
        choice4.setOnClickListener(this);
        reserveButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choice1:
                resData.setNumPeople(1);
                ((CardView) findViewById(R.id.cardChoice1)).setCardBackgroundColor(Color.GRAY);
                ((CardView) findViewById(R.id.cardChoice2)).setCardBackgroundColor(Color.WHITE);
                ((CardView) findViewById(R.id.cardChoice3)).setCardBackgroundColor(Color.WHITE);
                ((CardView) findViewById(R.id.cardChoice4)).setCardBackgroundColor(Color.WHITE);
                break;
            case R.id.choice2:
                resData.setNumPeople(2);
                ((CardView) findViewById(R.id.cardChoice1)).setCardBackgroundColor(Color.WHITE);
                ((CardView) findViewById(R.id.cardChoice2)).setCardBackgroundColor(Color.GRAY);
                ((CardView) findViewById(R.id.cardChoice3)).setCardBackgroundColor(Color.WHITE);
                ((CardView) findViewById(R.id.cardChoice4)).setCardBackgroundColor(Color.WHITE);
                break;
            case R.id.choice3:
                resData.setNumPeople(3);
                ((CardView) findViewById(R.id.cardChoice1)).setCardBackgroundColor(Color.WHITE);
                ((CardView) findViewById(R.id.cardChoice2)).setCardBackgroundColor(Color.WHITE);
                ((CardView) findViewById(R.id.cardChoice3)).setCardBackgroundColor(Color.GRAY);
                ((CardView) findViewById(R.id.cardChoice4)).setCardBackgroundColor(Color.WHITE);
                break;
            case R.id.choice4:
                resData.setNumPeople(4);
                ((CardView) findViewById(R.id.cardChoice1)).setCardBackgroundColor(Color.WHITE);
                ((CardView) findViewById(R.id.cardChoice2)).setCardBackgroundColor(Color.WHITE);
                ((CardView) findViewById(R.id.cardChoice3)).setCardBackgroundColor(Color.WHITE);
                ((CardView) findViewById(R.id.cardChoice4)).setCardBackgroundColor(Color.GRAY);
                break;
            case R.id.reserveButton:
                if(resData.getNumPeople() == 0){
                    Toast.makeText(RestaurantMenu2.this, "Please choose one choice!", Toast.LENGTH_SHORT).show();
                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("RestaurantPart").setValue(resData);
                    Toast.makeText(RestaurantMenu2.this, "Reserved ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, HomePage.class));
                }
                break;
        }

    }


    public static Intent getIntent(Context context) {
        return new Intent(context, RestaurantMenu2.class);
    }
}