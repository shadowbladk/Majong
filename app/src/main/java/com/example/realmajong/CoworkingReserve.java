package com.example.realmajong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CoworkingReserve extends AppCompatActivity implements View.OnClickListener{

    CoworkingSpace csData;
    TextView storeName, storeAddress;
    ImageView storeIcon, choice1, choice2, choice3, choice4, temp;
    Button reserveButton;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choice1:
                if(temp != null) temp.setBackgroundColor(Color.WHITE);
                choice1.setBackgroundColor(Color.GRAY);
                csData.setnumPeople(1);
                temp = choice1;
                break;
            case R.id.choice2:
                if(temp != null) temp.setBackgroundColor(Color.WHITE);
                choice2.setBackgroundColor(Color.GRAY);
                csData.setnumPeople(2);
                temp = choice2;
                break;
            case R.id.choice3:
                if(temp != null) temp.setBackgroundColor(Color.WHITE);
                choice3.setBackgroundColor(Color.GRAY);
                csData.setnumPeople(3);
                temp = choice3;
                break;
            case R.id.choice4:
                if(temp != null) temp.setBackgroundColor(Color.WHITE);
                choice4.setBackgroundColor(Color.GRAY);
                csData.setnumPeople(4);
                temp = choice4;
                break;
            case R.id.reserveButton:
                if(csData.getnumPeople() == 0){
                    Toast.makeText(CoworkingReserve.this, "Please choose one choice!", Toast.LENGTH_SHORT).show();
                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("CoworkingSpacePart").setValue(csData);
                    Toast.makeText(CoworkingReserve.this, "Reserved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, HomePage.class));
                }
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coworking_reserve);

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
            csData = new CoworkingSpace(extra.getString("name"), extra.getString("address"), extra.getString("path"));
        }

        storeIcon.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("@drawable/" + csData.getPath(), null, getPackageName())));
        storeName.setText(csData.getName());
        storeAddress.setText(csData.getAddress());

        choice1.setOnClickListener(this);
        choice2.setOnClickListener(this);
        choice3.setOnClickListener(this);
        choice4.setOnClickListener(this);
        reserveButton.setOnClickListener(this);

    }

    public static Intent getIntent(Context context){
        return new Intent(context, CoworkingReserve.class);
    }
}