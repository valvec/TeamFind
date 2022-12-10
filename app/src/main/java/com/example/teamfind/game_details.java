package com.example.teamfind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class game_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        Intent intent = getIntent();
        String gameName = intent.getStringExtra("name");
        String gameDesc = intent.getStringExtra("description");


        TextView Name = findViewById(R.id.title);
        Name.setText(gameName);
        TextView Desc = findViewById(R.id.description);
        Desc.setText(gameDesc);



// Set the number of stars to display
        ratingBar.setRating(3.2f);
    }

    public void rate(View view) {
    }

    public void subscribe(View view) {
    }
}