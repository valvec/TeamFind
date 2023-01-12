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

class register_service{
    private final String username;
    private final String password;
    private final String urlStoritve;
    private final String language;
    private final String email;
    private final Activity callerActivity;


    public register_service(String username, String password, String language, String email,  Activity callerActivity) {
        this.username = username;
        this.password = password;
        this.callerActivity = callerActivity;
        this.language=language;
        this.email= email;


        urlStoritve = callerActivity.getString(R.string.URL_base_storitve) + callerActivity.getString(R.string.URL_rel_register);
        //urlStoritve ="http://192.168.1.95/TF/user.php?username=user&amp;intent=login";
    }

    public String register() {
        ConnectivityManager connMgr = (ConnectivityManager) callerActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;

        try {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        catch (Exception e){

            return callerActivity.getResources().getString(R.string.napaka_omrezje);
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                int responseCode = connect(username, password);

                if(responseCode==201){
                    Global.username=username;
                    Global.password=password;
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

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the content as a InputStream, which it returns as a string.
    private int connect(String username, String password) throws IOException {
        URL url = new URL(urlStoritve);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000 /* milliseconds */);
        conn.setConnectTimeout(10000 /* milliseconds */);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoInput(true);

        try {
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password", password);
            json.put("email", email);
            json.put("language", language);

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