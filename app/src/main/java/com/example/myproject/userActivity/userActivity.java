package com.example.myproject.userActivity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myproject.Async_Api_request.AsyncChampDatas;
import com.example.myproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class userActivity extends FragmentActivity {
    protected String pseudo;
    //The key can be deppreciated
    public static String APIKey = "RGAPI-49d87a73-d332-4b6a-9dea-6267fb86f128";
    protected String summonerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intent = getIntent();
        pseudo = intent.getStringExtra("name");
        Log.i("Pseudo",pseudo);

        AsyncChampDatas req = new AsyncChampDatas();
        String url = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+ pseudo + "?api_key=" + userActivity.APIKey;
        req.execute(url);
        JSONObject PersonData = null;

        try {
            PersonData = (JSONObject) req.get();
            summonerID = PersonData.getString("id");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.i("Data",PersonData.toString());
        try {
            setBasicInformations(PersonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        getSupportFragmentManager().beginTransaction().replace(R.id.MyFragmentLayout, new Masteries_Fragment()).commit();

        final Switch turn = findViewById(R.id.switch1);
        turn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(turn.isChecked()){
                    getSupportFragmentManager().beginTransaction().replace(R.id.MyFragmentLayout, new Actual_Game_Fragment()).commit();
                }else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.MyFragmentLayout, new Masteries_Fragment()).commit();
                }
            }
        });
    }

    private void setBasicInformations(JSONObject personData) throws JSONException {
        ImageView ProfileIcon = findViewById(R.id.ProfileIcon);
        String idProfil = personData.getString("profileIconId");
        String profileURL = "http://ddragon.leagueoflegends.com/cdn/9.7.1/img/profileicon/"+idProfil+".png";
        Glide.with(this)
                .load(profileURL)
                .into(ProfileIcon);
        TextView name = (TextView) findViewById(R.id.ProfileName);
        name.setText(pseudo);
        String getRank = "https://euw1.api.riotgames.com/lol/league/v4/positions/by-summoner/"+ this.summonerID + "?api_key=" + userActivity.APIKey;
        AsyncChampDatas getRanks = new AsyncChampDatas();
        getRanks.execute(getRank);
        JSONArray Rank = null;
        try {
            System.out.println(getRank);
            Rank = (JSONArray) getRanks.get();
            Log.i("Rank",Rank.toString());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i=0;i<Rank.length();i++){
            JSONObject act = Rank.getJSONObject(i);
            if(act.getString("queueType").equals("RANKED_SOLO_5x5")){
                String Rankstring ="Niveau actuel : ";
                Rankstring += act.getString("tier");
                Rankstring += act.getString("rank");
               TextView t = findViewById(R.id.Rank);
               System.out.println("BLABLABLA" + Rankstring);

                   t.setText(Rankstring);

            }
        }
        Log.i("RANK",Rank.toString());
    }

}
