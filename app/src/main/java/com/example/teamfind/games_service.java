package com.example.teamfind;

import static com.example.teamfind.Global.password;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Objects;

class games_service{
    private final String username;
    private final boolean for_user;
    private final String urlStoritve;
    private final Activity callerActivity;

    public games_service(String username, boolean for_user,Activity callerActivity) {
        this.username = username;
        this.for_user=for_user;
        this.callerActivity = callerActivity;

        // ker ta razred ni storitev, moramo do resource-ov dostopati preko klicatelja, ki je storitev
        if (for_user) {
            urlStoritve = callerActivity.getString(R.string.URL_base_storitve) + callerActivity.getString(R.string.URL_rel_games_user);
        }
        else{
            urlStoritve = callerActivity.getString(R.string.URL_base_storitve) + callerActivity.getString(R.string.URL_rel_games);
        }



    }

    public JSONArray get_games() {
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

                return connect(username);

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
    private JSONArray connect(String username) throws IOException {
        URL url;
        if (for_user) {
            url = new URL(urlStoritve + "&username=" + username);
        }
        else{
            url = new URL(urlStoritve);
        }

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000 /* milliseconds */);
        conn.setConnectTimeout(10000 /* milliseconds */);
        if (for_user) {
            conn.setRequestMethod("POST");
        }
        else{
            conn.setRequestMethod("GET");
        }

        conn.setRequestProperty("Accept", "application/json");
        conn.setDoInput(true);

        if (for_user) {
            try {
                JSONObject json = new JSONObject();
                json.put("username", username);
                json.put("password", password);


                // Starts the query
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(json.toString());
                writer.flush();
                writer.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    public int game_interact(String ID, String intent, int subscribed,int rating){
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

            return connect_2_interact(ID,intent, subscribed, rating);

        } catch (IOException e) {
            e.printStackTrace();
            // return callerActivity.getResources().getString(R.string.napaka_storitev);
            }
        }
        else{
        // return callerActivity.getResources().getString(R.string.napaka_omrezje);
         }
        return -2;
    }

    private int connect_2_interact(String ID,String intent, int subscribed ,int rating) throws IOException {
        URL url = new URL(urlStoritve+"?username="+username+"&intent="+intent);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000 /* milliseconds */);
        conn.setConnectTimeout(10000 /* milliseconds */);
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoInput(true);
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoInput(true);

        try {
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password",password);
            json.put("game_id", ID);

            if ("rate".equals(intent)){
                json.put("rating", rating);
                Log.e("gg", String.valueOf(rating));
                Log.e("gg", ID);
            }
            if ("subscribe".equals(intent)){
                json.put("subscribed", subscribed);
            }

            // Starts the query
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(json.toString());
            writer.flush();
            writer.close();
            os.close();

            // blokira, dokler ne dobi odgovora
            int response = conn.getResponseCode();
            return response;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;

    }





    public JSONObject get_relation(String game_id) {
        ConnectivityManager connMgr = (ConnectivityManager) callerActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;

        try {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        catch (Exception e){

        }
        if (networkInfo != null && networkInfo.isConnected()) {
            try {

                return connectforrelation(username, game_id);

            } catch (IOException | JSONException e) {
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
    private JSONObject connectforrelation(String username, String game_id) throws IOException, JSONException {
        URL url;

        url = new URL(urlStoritve + "?intent=get_game_relation&username=" + username + "&game_id=" + game_id);


        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000 /* milliseconds */);
        conn.setConnectTimeout(10000 /* milliseconds */);
        conn.setRequestMethod("GET");

        conn.setRequestProperty("Accept", "application/json");
        conn.setDoInput(true);

        int response = conn.getResponseCode();

        String responseAsString = convertStreamToString(conn.getInputStream());
        Log.e("here",responseAsString);
        return new JSONObject(responseAsString);
    }


}