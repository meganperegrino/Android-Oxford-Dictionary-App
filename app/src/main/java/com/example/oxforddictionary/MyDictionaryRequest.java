package com.example.oxforddictionary;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MyDictionaryRequest extends AsyncTask<String, Integer, String> {

    final String app_id = "1d862174";
    final String app_key = "f7303482c8c309c182e3b5281b2415f5";

    String myurl;

    Context context;

    MyDictionaryRequest(Context context){

        this.context = context;
    }

    @Override
    protected String doInBackground(String... params){

        myurl = params[0];
        try {
            URL url = new URL(myurl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);

        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
