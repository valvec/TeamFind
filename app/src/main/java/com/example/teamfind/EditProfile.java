package com.example.teamfind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

public class EditProfile extends AppCompatActivity  implements View.OnClickListener{


    int game_times=0;
    String language;
    String new_password;
    String contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        try {
            TextView tv = findViewById(R.id.edit_contact);
            tv.setText(Global.USER_JSON_OBJECT.getString("contact"));
            tv = findViewById(R.id.edit_pass);
            tv.setText(Global.password);
            tv = findViewById(R.id.edit_language);
            tv.setText(Global.USER_JSON_OBJECT.getString("language"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

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

        if (time.length() ==3){
            hour=Integer.parseInt(time.toString().substring(0,2));
        }
        int game_hour_coded= (int) Math.pow(2,hour);
        game_times = game_times | game_hour_coded;
/*
        try {
            JSONObject json_obj = Global.USER_JSON_ARRAY.getJSONObject(0);
            int prev_game_times = json_obj.getInt("game_times");
            Toast.makeText(this,String.valueOf(prev_game_times),Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();

        }

*/
        String b=String.valueOf(game_times);


        button.setBackgroundColor(Color.WHITE);


        Toast.makeText(this,b,Toast.LENGTH_SHORT).show();
    }


    public void Update(View view) {
        TextView lng_view = findViewById(R.id.edit_language);
        TextView pass_view=findViewById(R.id.edit_pass);
        TextView contact_view=findViewById(R.id.edit_contact);

        language=lng_view.getText().toString();
        new_password =pass_view.getText().toString();
        contact=contact_view.getText().toString();
        int game_days= 1|2|4|8|16|32|64;

        edit_service edit_service=new edit_service(Global.username,Global.password,language, new_password,contact,game_times,game_days,this);
        new Thread(){
            @Override
            public void run(){
                String sucess=edit_service.update();
                runOnUiThread(()-> onUIThread(sucess));

            }

        }.start();
    }
    public void onUIThread(String sucess){
        Toast.makeText(getApplicationContext(),sucess,Toast.LENGTH_LONG).show();
        if (sucess.equals(getResources().getString(R.string.update_sucesfull))){
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);

        }
    }
}