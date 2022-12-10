package com.example.teamfind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void matchmaker(View view) {
        Intent intent = new Intent(this, Matchmaker.class);
        startActivity(intent);

    }

    public void Edit(View view) {
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);
    }

    public void games(View view) {
        Intent intent = new Intent(this, games.class);
        startActivity(intent);
    }
}