package com.example.oxforddictionary;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


import javax.net.ssl.HttpsURLConnection;

public class MyRequestAsync extends AsyncTask<String, Integer, String>{

    final String app_id = "1d862174";
    final String app_key = "f7303482c8c309c182e3b5281b2415f5";

    Context context;
    TextView t1;
    EditText e1;

    MyRequestAsync(Context context, TextView t1, EditText e1){

        this.context = context;
        this.t1 = t1;
        this.e1 = e1;
    }

    String myurl;
    StringBuilder myurl2;

    @Override
    protected String doInBackground(String... params){

        myurl = params[0];
        String result = "";

        myurl2 = new StringBuilder();
        myurl2.append(myurl);
        myurl2.append(e1.getText().toString());

        try {
            //URL url = new URL(myurl);
            URL url = new URL(myurl2.toString());
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            /*Handler handler = new Handler();
            handler.post(new Runnable(){
                @Override
                public void run(){
                    Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
                }
            });*/

            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            result = stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();

        }

        return result;
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);


        String def;
        try{
            JSONObject js = new JSONObject(s);
            JSONArray results = js.getJSONArray("results");

            JSONObject lEntries = results.getJSONObject(0);
            JSONArray laArray = lEntries.getJSONArray("lexicalEntries");

            JSONObject entries = laArray.getJSONObject(0);
            JSONArray e = entries.getJSONArray("entries");

            JSONObject jsonObject = e.getJSONObject(0);
            JSONArray sensesArray = jsonObject.getJSONArray("senses");

            JSONObject d = sensesArray.getJSONObject(0);
            JSONArray de = d.getJSONArray("definitions");

            def = de.getString(0);

            t1.setText(def);
            //Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            //t1.setText(e1.getText().toString());
        }
        catch(JSONException e){
            e.printStackTrace();
        }

    }
}
