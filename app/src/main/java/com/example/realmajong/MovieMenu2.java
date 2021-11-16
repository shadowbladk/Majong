package com.example.realmajong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MovieMenu2 extends AppCompatActivity implements View.OnClickListener {

    MovieData movie;
    TextView movieName, movieDate, movieGenre, movieTime, textShowtime, textInformation, textDescription, dateSelected, numberOfSeats, seatsSelected;
    ImageView image;
    MaterialCardView cardInfo, cardSelectShowtime;
    ArrayList<TextView> textDateList;
    Button buttonReserve;
    ArrayList<Button> seatList;
    int numRows = 3;

    int seatsPerRow = 8;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_menu2);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            movie = new MovieData(extra.getString("name"), extra.getString("date"), extra.getString("path"), extra.getString("genre"), extra.getString("time"), extra.getString("description"));
        }




        Log.i("movie name", movie.getName());
        Log.i("movie date", movie.getDate());
        Log.i("movie path", movie.getPath());

        movieName = findViewById(R.id.movieName);
        movieDate = findViewById(R.id.movieDate);
        movieGenre = findViewById(R.id.movieGenre);
        movieTime = findViewById(R.id.movieTime);
        textShowtime = findViewById(R.id.textShowTime);
        textInformation = findViewById(R.id.textInformation);
        textDescription = findViewById(R.id.textDescription);
        image = findViewById(R.id.movieImage);
        cardInfo = findViewById(R.id.cardInfo);
        cardSelectShowtime = findViewById(R.id.cardSelectShowtime);
        dateSelected = findViewById(R.id.dateSelected);
        numberOfSeats = findViewById(R.id.numberOfSeats);
        buttonReserve = findViewById(R.id.buttonReserve);
        seatsSelected = findViewById(R.id.seatsSelected);

        movieName.setText(movie.getName());
        movieDate.setText(movie.getDate());
        movieGenre.setText(movie.getGenre());
        movieTime.setText(movie.getTime());
        textDescription.setText(movie.getDescription());
        textShowtime.setOnClickListener(this);
        textInformation.setOnClickListener(this);
        image.setBackground(getResources().getDrawable(getResources().getIdentifier("@drawable/" + movie.getPath(), null, getPackageName())));
        textShowtime.setPaintFlags(textShowtime.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView textMonth = findViewById(R.id.textMonth);
        textMonth.setText(R.string.month);

        Animation ranim = AnimationUtils.loadAnimation(this, R.anim.myanim);
        ranim.setFillAfter(true);
        textMonth.setAnimation(ranim);

        RelativeLayout layoutDates = findViewById(R.id.layoutDates);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("EEE", Locale.getDefault());

        format = new SimpleDateFormat("dd", Locale.getDefault());
        int date = Integer.parseInt(format.format(calendar.getTime()));

        textDateList = new ArrayList<>();
        for (int i = date; i <= 30; i++) {
            calendar.set(2021, 11, i+ 5);

            String day = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            if (day != null) {
                day = day.toUpperCase(Locale.ROOT).substring(0, 3);
            }

            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            if (i != date) {
                rlp.addRule(RelativeLayout.RIGHT_OF, textDateList.get(i - date - 1).getId());
            }
            rlp.addRule(RelativeLayout.CENTER_VERTICAL);
            rlp.setMargins(10, 0, 10, 0);

            TextView textDate = new TextView(this);
            String str = day + "\n" + i;
            textDate.setText(str);
            textDate.setTextColor(Color.WHITE);
            textDate.setTextSize(15);
            textDate.setLayoutParams(rlp);
            textDate.setGravity(Gravity.CENTER);
            textDate.setId(View.generateViewId());
            textDate.setOnClickListener(selectDate);
            textDate.setTag(R.id.dayDate, (String) day + " " + i);

            layoutDates.addView(textDate);
            textDateList.add(textDate);
        }



        buttonReserve.setOnClickListener(view -> {
            if (Integer.parseInt(numberOfSeats.getText().toString()) != 0 && !dateSelected.getText().toString().equals("-") && !seatsSelected.getText().toString().equals("-")) {
                Log.e("data", "Reserved on " + dateSelected.getText().toString() + " for " + numberOfSeats.getText().toString() + " people");
                MovieReservation movieReservation = new MovieReservation(movie.getName(),dateSelected.getText().toString(), new ArrayList<>(Arrays.asList(seatsSelected.getText().toString().split(" "))));
                FirebaseDatabase.getInstance().getReference().child("MoviePart").push().setValue(movieReservation);
                Toast.makeText(MovieMenu2.this, "Reserved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomePage.class));
            } else {
                Toast.makeText(MovieMenu2.this, "Please choose date and seats!!!", Toast.LENGTH_SHORT).show();
                Log.e("data", "INVALID");
            }
        });

    }



    public void createSeating(String date) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("MoviePart");

        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);

        seatList = new ArrayList<>();
        ArrayList<LinearLayout> linearLayoutList = new ArrayList<>();
        for (int row = 0; row < numRows; row++) {
            LinearLayout linearLayout = new LinearLayout(this);

            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            if (row != 0) {
                rlp.addRule(RelativeLayout.BELOW, linearLayoutList.get(row - 1).getId());
            }

            linearLayout.setId(View.generateViewId());
            linearLayout.setLayoutParams(rlp);

            for (int col = 0; col < seatsPerRow; col++) {
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, cardSelectShowtime.getLayoutParams().height / numRows - 30);
                Log.i("Card Seating Height", String.valueOf(cardSelectShowtime.getLayoutParams().height));
                llp.weight = 1;
                llp.setMargins(15, 15, 15, 15);


                String seatID = (char)(row + 65) + Integer.toString(col + 1);

                Button button = new Button(this);

                button.setBackgroundColor(Color.RED);
                button.setOnClickListener(buttonSeating);
                button.setLayoutParams(llp);
                button.setId(View.generateViewId());
                button.setTag(R.id.id, seatID);
                button.setTag(R.id.data, 0);

                Log.e("Char", String.valueOf((char)(row + 65)));

                button.setText(seatID);
                seatList.add(button);
                linearLayout.addView(button);
            }
            linearLayoutList.add(linearLayout);
            relativeLayout.addView(linearLayout);
        }


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ss: snapshot.getChildren()) {
                    MovieReservation movieReservation = ss.getValue(MovieReservation.class);

                    Log.e("movieReservation Date", movieReservation.getDateSelected());
                    Log.e("date", date);

                    assert movieReservation != null;
                    if (!movieReservation.getName().equals(movie.getName()) || !movieReservation.getDateSelected().equals(date)) {
                        Log.e("Invalid", "Invalid");
                        continue;
                    }
                    Log.e("Valid", "Valid");
                    for (String seat: movieReservation.getSeats()) {
                        int seatIndex = seatsPerRow * (((int) seat.charAt(0)) - 65) + Character.getNumericValue(seat.charAt(1)) - 1;
                        seatList.get(seatIndex).setOnClickListener(null);
                        seatList.get(seatIndex).setBackgroundColor(Color.GRAY);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public static Intent getIntent(Context context) {
        return new Intent(context, MovieMenu2.class);
    }

    public View.OnClickListener buttonSeating = new View.OnClickListener() {
        public void onClick(View v) {
            StringBuilder str;
            if ((int) v.getTag(R.id.data) == 0) {
                v.setBackgroundColor(Color.GREEN);
                v.setTag(R.id.data, 1);
                numberOfSeats.setText(String.valueOf(Integer.parseInt(numberOfSeats.getText().toString()) + 1));
                if (seatsSelected.getText().equals("-")) {
                    seatsSelected.setText("");
                }
                String s = seatsSelected.getText() + v.getTag(R.id.id).toString() + " ";
                ArrayList<String> arr = new ArrayList<>(Arrays.asList(s.split(" ")));
                Collections.sort(arr);
                str = new StringBuilder();
                for (String string: arr)
                    str.append(string).append(" ");
                seatsSelected.setText(str);

            } else {
                v.setBackgroundColor(Color.RED);
                v.setTag(R.id.data, 0);
                numberOfSeats.setText(String.valueOf(Integer.parseInt(numberOfSeats.getText().toString()) - 1));
                ArrayList<String> seatList = new ArrayList<>(Arrays.asList(seatsSelected.getText().toString().split(" ")));
                str = new StringBuilder();
                for (String s: seatList) {
                    if (!s.equals(v.getTag(R.id.id))) {
                        str.append(s).append(" ");
                    }
                }
                if (str.toString().equals("")) { str = new StringBuilder("-"); }
                seatsSelected.setText(str.toString());
            }
        }
    };

    public View.OnClickListener selectDate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for (int i = 0; i < textDateList.size(); i++) {
                if (view.getId() == textDateList.get(i).getId()) {
                    textDateList.get(i).setTextColor(getResources().getColor(R.color.gold));
                    dateSelected.setText((String) textDateList.get(i).getTag(R.id.dayDate));
                } else {
                    textDateList.get(i).setTextColor(Color.WHITE);
                }
            }

            numberOfSeats.setText("0");
            seatsSelected.setText("-");
            createSeating(view.getTag(R.id.dayDate).toString());
            findViewById(R.id.cardReserveInfo).setVisibility(View.VISIBLE);
        }
    };

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.textShowTime:
                textShowtime.setTextColor(getResources().getColor(R.color.gold));
                textShowtime.setPaintFlags(textShowtime.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                textInformation.setPaintFlags(textInformation.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                textInformation.setTextColor(Color.WHITE);
                cardInfo.setVisibility(View.GONE);
                cardSelectShowtime.setVisibility(View.VISIBLE);
                break;
            case R.id.textInformation:
                textInformation.setTextColor(getResources().getColor(R.color.gold));
                textInformation.setPaintFlags(textInformation.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                textShowtime.setPaintFlags(textShowtime.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                textShowtime.setTextColor(Color.WHITE);
                cardInfo.setVisibility(View.VISIBLE);
                cardSelectShowtime.setVisibility(View.GONE);
                break;
        }
    }
}