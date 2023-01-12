package com.example.teamfind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Spinner spino = findViewById(R.id.language_spinner);
        List<String> languages = new ArrayList<String>();

        lang lang= new lang(this);

        new Thread(){
            @Override
            public void run(){
                JSONArray langs=lang.lang();
                runOnUiThread(()-> language(langs));

            }

            private void language(JSONArray langs) {
                for (int i = 0; i < langs.length(); i++) {
                    JSONObject c = null;
                    try {
                        c = langs.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String lang = null;
                    try {
                        lang = c.getString("lang_eng");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    languages.add(lang);


                    String[] simpleArray = new String[ languages.size() ];
                    languages.toArray( simpleArray );

                    ArrayAdapter ad
                            = new ArrayAdapter(
                            register.this,
                            android.R.layout.simple_spinner_item,
                            languages);

                    ad.setDropDownViewResource(
                            android.R.layout
                                    .simple_spinner_dropdown_item);

                    spino.setAdapter(ad);




                }
            }

        }.start();


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

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    }






