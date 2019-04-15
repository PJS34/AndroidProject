package com.example.myproject.Async_Api_request;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AsyncChampDatas extends AsyncTask<String, Void, Object> {

    public AsyncChampDatas(){
    }
    @Override
    protected Object doInBackground(String... strings) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        String result = null;
        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection(); // Open
            InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // Stream

            result = readStream(in); // Read stream

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        JSONObject json = null;
        try {
            Log.i("RESULT",result);
            json = new JSONObject(result);
        } catch (JSONException e) {
            try {
                JSONArray myArray = new JSONArray(result);
                return (Object) myArray;
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.i("ERROR","Cannot convert to json");
            e.printStackTrace();
        }
        return (Object) json;
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
    }
    public static ArrayList<String> ExtractJSON(JSONObject json){
        ArrayList myList = new ArrayList<String>();

            for(Iterator<String> it = json.keys(); it.hasNext();){
                {
                    myList.add(it.next());
                }
            }
            Log.i("Names",myList.toString());

        return myList;
    }
   /* public static HashMap<String,String> ExtractaMapfromJson(JSONObject json) throws JSONException {
        HashMap<String,String> MyDatas = new HashMap<>();
        for(Iterator<String> it = json.keys(); it.hasNext();){
            {
                String nextKey = it.next();
                MyDatas.put(nextKey,json.get(nextKey));
            }
        }
    }*/
}
