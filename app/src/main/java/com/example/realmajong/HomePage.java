package com.example.realmajong;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    private CardView restCard, movCard, coworkingCard, incomingCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        restCard = (CardView) findViewById(R.id.restCard);
        movCard = (CardView) findViewById(R.id.movCard);
        coworkingCard = (CardView) findViewById(R.id.coworkingCard);
        incomingCard = (CardView) findViewById(R.id.incomingCard);

        restCard.setOnClickListener(this);
        movCard.setOnClickListener(this);
        coworkingCard.setOnClickListener(this);
        incomingCard.setOnClickListener(this);

    }

    public static Intent getIntent(Context context) {
        return new Intent(context, HomePage.class);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch(v.getId()){
            case R.id.movCard:
                i = MovieMenu.getIntent(this);
                startActivity(i);
                break;
            case R.id.restCard:
                i = RestaurantMenu.getIntent(this);
                startActivity(i);
                break;
            case R.id.coworkingCard:
                i = ChooseLocationCoworkingSpace.getIntent(this);
                startActivity(i);
                break;
            case R.id.incomingCard:
                Toast.makeText(HomePage.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        }
    }
}