package com.example.teamfind;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class lang {

    private final String urlStoritve;
    private final Activity callerActivity;

    public lang(Activity callerActivity) {
        this.callerActivity = callerActivity;
        urlStoritve = callerActivity.getString(R.string.URL_base_storitve) + callerActivity.getString(R.string.URL_rel_lang);
    }


    public JSONArray lang() {
        ConnectivityManager connMgr = (ConnectivityManager) callerActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;

        try {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        catch (Exception e){
            //je v manifestu dovoljenje za uporabo omrezja?
            //return callerActivity.getResources().getString(R.string.napaka_omrezje);
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            try {

                return connect();

            } catch (IOException e) {
                e.printStackTrace();
                // return callerActivity.getResources().getString(R.string.napaka_storitev);
            }
        }
        else{
            // return callerActivity.getResources().getString(R.string.napaka_omrezje);
        }
        return null;
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the content as a InputStream, which it returns as a string.
    private JSONArray connect() throws IOException {
        URL url = new URL(urlStoritve);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000 /* milliseconds */);
        conn.setConnectTimeout(10000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoInput(true);

        // blokira, dokler ne dobi odgovora
        int response = conn.getResponseCode();

        // Convert the InputStream into a string
        String responseAsString = convertStreamToString(conn.getInputStream());
        return JSONString2Array(responseAsString);
    }

    private String convertStreamToString(InputStream is) {
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

    public JSONArray JSONString2Array(String JsonString){
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(JsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }


}
