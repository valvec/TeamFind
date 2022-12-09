package com.example.teamfind;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfile extends AppCompatActivity  implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Button b = (Button) findViewById(R.id.h0);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h1);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h2);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h3);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h4);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h5);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h6);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h7);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h8);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h9);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h10);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h11);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h12);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h13);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h14);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h15);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h16);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h17);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h18);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h19);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h20);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h21);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h22);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.h23);
        b.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        Button button = (Button)v;
        CharSequence time = button.getText();
        int hour= Character.getNumericValue(time.charAt(0));

/*
        try {
            JSONObject json_obj = Global.USER_JSON_ARRAY.getJSONObject(0);
            int prev_game_times = json_obj.getInt("game_times");
            Toast.makeText(this,String.valueOf(prev_game_times),Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();

        }

*/
        String b=String.valueOf(hour);
        Toast.makeText(this,b,Toast.LENGTH_LONG).show();
    }


}