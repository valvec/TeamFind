package com.example.teamfind;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

class edit_service{
    private final String username;
    private final String password;
    private final String urlStoritve;
    private final String language;
    private final String new_pass;
    private final String contact;
    private final int game_times;
    private final Activity callerActivity;
    private final int game_days;

    public edit_service(String username, String password, String language, String new_pass, String contact, int game_times, int game_days, Activity callerActivity) {
        this.username = username;
        this.password = password;
        this.callerActivity = callerActivity;
        this.language=language;
        this.contact=contact;
        this.new_pass=new_pass;
        this.game_times=game_times;
        this.game_days=game_days;


        urlStoritve = callerActivity.getString(R.string.URL_base_storitve) + callerActivity.getString(R.string.URL_rel_update);
        //urlStoritve ="http://192.168.1.95/TF/user.php?username=user&amp;intent=login";
    }

    public String update() {
        ConnectivityManager connMgr = (ConnectivityManager) callerActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;

        try {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        catch (Exception e){
            //je v manifestu dovoljenje za uporabo omrezja?
            return callerActivity.getResources().getString(R.string.napaka_omrezje);
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                int responseCode = connect(username, password);

                if(responseCode==204){

                    Global.password=new_pass;
                    return callerActivity.getResources().getString(R.string.update_sucesfull);

                }
                else{
                    return callerActivity.getResources().getString(R.string.update_failed)+" "+responseCode;

                }
            } catch (IOException e) {
                e.printStackTrace();
                return callerActivity.getResources().getString(R.string.napaka_storitev);
            }
        }
        else{
            return callerActivity.getResources().getString(R.string.napaka_omrezje);
        }
    }


    private int connect(String username, String password) throws IOException {
        URL url = new URL(urlStoritve+"&username="+username);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000 /* milliseconds */);
        conn.setConnectTimeout(10000 /* milliseconds */);
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoInput(true);

        try {
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password", password);
            json.put("new_password", new_pass);
            json.put("contact", contact);
            json.put("language", language);
            json.put("game_times", game_times);
            json.put("game_days", game_days);

            // Starts the query
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(json.toString());
            writer.flush();
            writer.close();
            os.close();

            // blokira, dokler ne dobi odgovora
            int response = conn.getResponseCode();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn.getResponseCode();
    }




}