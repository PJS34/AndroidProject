package com.example.myproject.userActivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.myproject.Async_Api_request.AsyncChampDatas;
import com.example.myproject.R;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class userActivity extends Activity {
    private String pseudo;
    //The key can be deppreciated
    private String APIKey = "RGAPI-edeb1d74-d4ce-4039-81e3-ce42387c16fa";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intent = getIntent();
        pseudo = intent.getStringExtra("name");
        Log.i("Pseudo",pseudo);
        AsyncChampDatas req = new AsyncChampDatas();
        req.execute("https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + pseudo + "?" + APIKey);
        JSONObject PersonData = null;
        try {
            PersonData = req.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("Data",PersonData.toString());


    }
}
