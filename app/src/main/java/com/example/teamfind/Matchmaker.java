package com.example.teamfind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class Matchmaker extends AppCompatActivity {
    ListView user_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchmaker);
        user_list = findViewById(R.id.user_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



    }

    @Override
    protected void onStart() {
        super.onStart();
        matchmaker_service matchmaker_service= new matchmaker_service(Global.username,this);
        new Thread(){
            @Override
            public void run() {
                JSONArray podatki = matchmaker_service.matchmaker_service();
                runOnUiThread(()-> show(podatki));
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









        public void show(JSONArray jsonObject){


            ArrayList<HashMap<String, String>> listdata = new JSON_parser_matchmake().parseToArrayList(jsonObject);


        SimpleAdapter adapter = new SimpleAdapter(this,
                listdata,
                R.layout.list_item,
                new String[]{"username", "contact", "game","game_times","language"},
                new int[]{R.id.matchmaker_username, R.id.matchmaker_contact, R.id.matchmaker_game, R.id.matchmaker_gametime,R.id.matchmaker_language} //tisti @+id, so številke shranjene v R.id
        );
        user_list.setAdapter(adapter);
    }
}