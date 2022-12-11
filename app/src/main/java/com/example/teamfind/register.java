package com.example.teamfind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Spinner spino = findViewById(R.id.language_spinner);
        final String[] languages = { "Select Language","slovenian", "english",
                "croatian", "german",
                "chinese", "korean" };

        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                languages);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spino.setAdapter(ad);
    }



    public void Register(View view) {

        TextView tv=findViewById(R.id.email_input);
        String email = tv.getText().toString();
        tv=findViewById(R.id.username_input);
        String username = (String) tv.getText().toString();
        tv=findViewById(R.id.password_input);
        String pass = (String) tv.getText().toString();
        Spinner sv = findViewById(R.id.language_spinner);
        int position = sv.getSelectedItemPosition();
        String language = sv.getItemAtPosition(position).toString();


        register_service register_service=new register_service(username,pass,language,email,this);
        new Thread(){
            @Override
            public void run(){
                String sucess=register_service.register();
                runOnUiThread(()-> onUIThread(sucess));

            }

        }.start();
    }
    public void onUIThread(String sucess) {
        //Toast.makeText(getApplicationContext(), sucess, Toast.LENGTH_LONG).show();
        if (sucess.equals(getResources().getString(R.string.update_sucesfull))) {

            Login login= new Login(Global.username,Global.password, this);
            new Thread(){
                @Override
                public void run(){
                    login.login();
                    runOnUiThread(()-> {Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                    startActivity(intent);});

                }

            }.start();
        }
        }
    }






