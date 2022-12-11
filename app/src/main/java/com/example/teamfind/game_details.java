package com.example.teamfind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class game_details extends AppCompatActivity {
    Intent intent;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        intent = getIntent();
        String gameName = intent.getStringExtra("name");
        String gameDesc = intent.getStringExtra("description");


        TextView Name = findViewById(R.id.title);
        Name.setText(gameName);
        TextView Desc = findViewById(R.id.description);
        Desc.setText(gameDesc);
        ratingBar.setRating(Float.parseFloat(intent.getStringExtra("average_rating")));

        games_service games_service = new games_service(Global.username,false, this);
        new Thread() {
            @Override
            public void run() {
                JSONObject podatki = games_service.get_relation(intent.getStringExtra("ID"));
                runOnUiThread(() -> User_pref(podatki));
            }
        }.start();


// Set the number of stars to display

    }

    private void User_pref(JSONObject data) {
            if (data != null ){
            try {
                String Users_rating = data.getString("rating");
                Toast.makeText(getApplicationContext(),Users_rating,Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }}

    }

    public void rate(View view) {
        games_service games_service=new games_service(Global.username,false,this);
        new Thread(){
            @Override
            public void run(){
                int sucess=games_service.game_interact(intent.getStringExtra("ID"),"rate",1, (int) ratingBar.getRating());
                runOnUiThread(()-> onUIThread(sucess));

            }

        }.start();
    }

    private void onUIThread(int sucess) {
        if (sucess==204){
            Toast.makeText(getApplicationContext(),"Sucess!",Toast.LENGTH_SHORT).show();
            Intent goBck=new Intent(this, games.class);
            startActivity(goBck);
        }
        else {
            Toast.makeText(getApplicationContext(),"ERROR: " + String.valueOf(sucess),Toast.LENGTH_SHORT).show();
        }
    }




    public void subscribe(View view) {
        games_service games_service=new games_service(Global.username,false,this);
        new Thread(){
            @Override
            public void run(){
                int sucess=games_service.game_interact(intent.getStringExtra("ID"),"subscribe",1,-1);
                runOnUiThread(()-> onUIThread(sucess));
            }

        }.start();



    }
}