package com.example.teamfind;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JSON_parser_matchmake {

        private static final String TAG = JSON_parser_matchmake.class.getSimpleName();
        private ArrayList<HashMap<String, String>> userslist = new ArrayList<>();

        public ArrayList<HashMap<String, String>> parseToArrayList(JSONArray users){
            try {


                // Getting JSON Array node

                // looping through All Contacts
                for (int i = 0; i < users.length(); i++) {
                    JSONObject c = users.getJSONObject(i);

                    String username = c.getString("username");
                    String contact = c.getString("contact");
                    String game = c.getString("name");
                    //String address = c.getString("address");
                    //String gender = c.getString("gender");


                    // tmp hash map for single contact
                    HashMap<String, String> user = new HashMap<>();

                    // adding each child node to HashMap key => value
                    user.put("username", username);
                    user.put("contact", contact);
                    user.put("game", game);


                    // adding contact to contact list
                    userslist.add(user);

                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
            return userslist;
        }
    }

