package com.example.teamfind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mainActivity=this;

    }

    public void Login(View view) {
        TextView username_view = findViewById(R.id.username);
        TextView pass_view = findViewById(R.id.pass);
        String username = username_view.getText().toString();
        String pass=pass_view.getText().toString();

        Login login= new Login(username,pass, mainActivity);
        new Thread(){
            @Override
            public void run(){
                String sucess=login.login();
                runOnUiThread(()-> onUIThread(sucess));

            }

            }.start();
        }
        public void onUIThread(String sucess){
            Toast.makeText(getApplicationContext(),sucess,Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(),Global.username2,Toast.LENGTH_LONG).show();
            if (sucess.equals(getResources().getString(R.string.login_sucesfull))){
                Intent intent = new Intent(this, MainMenu.class);
                startActivity(intent);

            }


        }

    public void Register_activity(View view) {
        Intent intent = new Intent(this, register.class);
        startActivity(intent);

    }
}


