package com.example.teamfind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class game_details extends AppCompatActivity {
    Intent intent;
    RatingBar ratingBar;
    int subscribe_intent =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        intent = getIntent();
        String gameName = intent.getStringExtra("name");
        String gameDesc = intent.getStringExtra("description");



        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


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




    }



    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void User_pref(JSONObject data) {
            if (data != null ){
            try {
                String Users_rating = data.getString("rating");
                TextView tv=findViewById(R.id.myrating);
                tv.setText("My rating: "+Users_rating);
                int sub= Integer.parseInt(data.getString("subscribed"));
                if (sub!=0){
                    Button btn = findViewById(R.id.subscribe_button);
                    btn.setText("UNSUBSCRIBE");
                    subscribe_intent=0;
                }

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
                int sucess=games_service.game_interact(intent.getStringExtra("ID"),"subscribe",subscribe_intent,-1);
                runOnUiThread(()-> onUIThread(sucess));
            }

        }.start();



    }
}