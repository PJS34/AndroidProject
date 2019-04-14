package com.example.myproject.Async_Api_request;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class AsyncChampDatas extends AsyncTask<String, Void, JSONObject> {
    private Activity myaActivity;
    ArrayList<String> Names;

    public AsyncChampDatas(){
       // this.myaActivity=Activity;
        Names = new ArrayList<>();
    }
    @Override
    protected JSONObject doInBackground(String... strings) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        String result = null;
        try {
            url = new URL("http://ddragon.leagueoflegends.com/cdn/9.6.1/data/en_US/champion.json");
            urlConnection = (HttpURLConnection) url.openConnection(); // Open
            InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // Stream

            result = readStream(in); // Read stream

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        JSONObject json = null;
        try {
            Log.i("RESULT",result);
            json = new JSONObject(result);
        } catch (JSONException e) {
            Log.i("ERROR","Cannot convert to json");
            e.printStackTrace();
        }

        if(json == null){
            System.out.println("JSON NULL");
        }

        try {
            JSONObject Data = json.getJSONObject("data");

            for(Iterator<String> it = Data.keys();it.hasNext();){
                {
                    this.Names.add(it.next());
                }
            }
            Log.i("Names",this.Names.toString());
        } catch (JSONException e) {
            System.err.println("Can't fill the array");
            e.printStackTrace();
        }
        return json;
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){

            sb.append(line);
        }
        is.close();
        // Extracting the JSON object from the String
        Log.i("LINE",sb.toString());
        return sb.toString();
    }

    protected void onPostExecute(JSONObject s) {
        Log.i("JSON ORIGINAL",s.toString() );

    }

    public ArrayList getList() {

        return this.Names;
    }
}
