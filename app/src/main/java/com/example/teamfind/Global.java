package com.example.teamfind;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Global {
    static String username;
    static String password;
    static JSONArray USER_JSON_ARRAY;


    static public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    static public JSONArray JSONString2Array(String JsonString){
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(JsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }}