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

class Login{
    private final String username;
    private final String password;
    private final String urlStoritve;
    private final Activity callerActivity;

    public Login(String username, String password, Activity callerActivity) {
        this.username = username;
        this.password = password;
        this.callerActivity = callerActivity;

        // ker ta razred ni storitev, moramo do resource-ov dostopati preko klicatelja, ki je storitev
        urlStoritve = callerActivity.getString(R.string.URL_base_storitve) + callerActivity.getString(R.string.URL_rel_login);
        //urlStoritve ="http://192.168.1.95/TF/user.php?username=user&amp;intent=login";
    }

    public String login() {
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

                if(responseCode==200){
                    return callerActivity.getResources().getString(R.string.login_sucesfull);
                }
                else{
                    return callerActivity.getResources().getString(R.string.login_failed)+" "+responseCode;

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
        URL url = new URL(urlStoritve+"&username="+username);

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
            Global.password=password;
            Global.username=username;

            // Starts the query
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(json.toString());
            writer.flush();
            writer.close();
            os.close();

            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);

            // blokira, dokler ne dobi odgovora
            int response = conn.getResponseCode();

            // Convert the InputStream into a string
            String responseAsString = Global.convertStreamToString(conn.getInputStream());
            Global.USER_JSON_ARRAY=Global.JSONString2Array(responseAsString);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn.getResponseCode();
    }




}