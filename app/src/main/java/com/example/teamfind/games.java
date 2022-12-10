package com.example.teamfind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class games extends AppCompatActivity {
    GridView gridView;
    JSONArray podatki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        gridView = findViewById(R.id.gridview);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the item at the clicked position
                Object item = parent.getItemAtPosition(position);
                try {
                    String game_name = podatki.getJSONObject(position).getString("name");
                    Toast.makeText(getApplicationContext(),game_name,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), game_details.class);
                    intent.putExtra("name",podatki.getJSONObject(position).getString("name"));
                    intent.putExtra("description",podatki.getJSONObject(position).getString("description"));
                    intent.putExtra("average_rating",podatki.getJSONObject(position).getString("average_rating"));
                    startActivity(intent);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Do something with the item
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        games_service games_service = new games_service(Global.username,false, this);
        new Thread() {
            @Override
            public void run() {
                podatki = games_service.get_games();
                runOnUiThread(() -> show(podatki));
            }
        }.start();
    }

    public void show(JSONArray jsonArray){

        List<Map<String, String>> data = new ArrayList<>();

        try {

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Map<String, String> item = new HashMap<>();
                item.put("name", jsonObject.getString("name"));
                item.put("average_rating", "Average rating: "+jsonObject.getString("average_rating"));
                data.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Set up the grid view
        GridView gridView = findViewById(R.id.gridview);
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.game_tile,
                new String[]{"name", "average_rating"}, new int[]{R.id.text1, R.id.text2});
        gridView.setAdapter(adapter);



    }





}