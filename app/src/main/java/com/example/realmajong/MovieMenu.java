package com.example.realmajong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.material.card.MaterialCardView;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class MovieMenu extends AppCompatActivity {

    public RelativeLayout layout;
    public ArrayList<MaterialCardView> cardList;
    ArrayList<MovieData> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_menu);

        String json = null;
        try {
            InputStream is = getAssets().open("movieData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MovieData>>() { }.getType();

        movieList = gson.fromJson(json, type);

        layout = findViewById(R.id.layout);
        cardList = new ArrayList<>();


        for (MovieData movie: movieList) {
            MaterialCardView cardView = new MaterialCardView(this);
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(Utility.dp2px(this, 206), Utility.dp2px(this, 388));

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

            ImageView black = new ImageView(this);

            rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,Utility.dp2px(this, 85));
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            black.setLayoutParams(rlp);
            black.setId(View.generateViewId());
            black.setBackgroundColor(Color.BLACK);

            ImageView image = new ImageView(this);

            rlp = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    Utility.dp2px(this, 303));
            rlp.addRule(RelativeLayout.ABOVE, black.getId());
            image.setLayoutParams(rlp);
            image.setBackground(ResourcesCompat.getDrawable(getResources(), getResources().getIdentifier("@drawable/" + movie.getPath(), null, getPackageName()), null));

            rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rlp.setMargins(Utility.dp2px(this, 10), Utility.dp2px(this, 313), 0, Utility.dp2px(this, 2));
            TextView movieDate = new TextView(this);
            movieDate.setLayoutParams(rlp);
            movieDate.setTextColor(Color.parseColor("#d4af37"));
            movieDate.setTextSize(12);
            movieDate.setId(View.generateViewId());
            movieDate.setText(movie.getDate().toUpperCase());


            rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rlp.addRule(RelativeLayout.BELOW, movieDate.getId());
            rlp.addRule(RelativeLayout.ALIGN_LEFT, movieDate.getId());
            TextView movieName = new TextView(this);
            movieName.setTextColor(Color.WHITE);
            movieName.setTextSize(16);
            movieName.setLayoutParams(rlp);
            movieName.setId(View.generateViewId());
            movieName.setText(movie.getName().toUpperCase());
            movieName.setTypeface(null, Typeface.BOLD);



            relativeLayout.addView(image);
            relativeLayout.addView(black);
            relativeLayout.addView(movieName);
            relativeLayout.addView(movieDate);

            cardView.addView(relativeLayout);
            layout.addView(cardView);


            cardView.setTag(cardList.size() - 1);
            cardView.setOnClickListener(switchMenu);
        }
    }

    public View.OnClickListener switchMenu = new View.OnClickListener() {
        public void onClick(View v) {
            int index = (int) v.getTag();
            Intent i = MovieMenu2.getIntent(MovieMenu.this);
            i.putExtra("name", movieList.get(index).getName());
            i.putExtra("date", movieList.get(index).getDate());
            i.putExtra("path", movieList.get(index).getPath());
            i.putExtra("genre", movieList.get(index).getGenre());
            i.putExtra("time", movieList.get(index).getTime());
            i.putExtra("description", movieList.get(index).getDescription());
            startActivity(i);
        }
    };

    public static Intent getIntent(Context context) {
        return new Intent(context, MovieMenu.class);
    }
}
