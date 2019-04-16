package com.example.myproject.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.myproject.Detailled_Champion.Display_One_Champion;
import com.example.myproject.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import com.example.myproject.Async_Api_request.AsyncChampDatas;
import com.example.myproject.userActivity.userActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class DisplayAllChars extends Activity {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_chars);
        final AsyncChampDatas getChamps = new AsyncChampDatas();
        getChamps.execute("http://ddragon.leagueoflegends.com/cdn/9.6.1/data/en_US/champion.json");

        Button go = findViewById(R.id.Go);
        go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText s = findViewById(R.id.Pseudo);
                String name = s.getText().toString();
                String url = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+ name + "?api_key=" + userActivity.APIKey;
                AsyncChampDatas req = new AsyncChampDatas();
                req.execute(url);
                JSONObject PersonData = null;
                try {
                    PersonData =(JSONObject) req.get();
                    System.out.println(url);
                    if(PersonData == null){
                        Toast.makeText(DisplayAllChars.this,"Nom de joueur incorrect",Toast.LENGTH_SHORT).show();
                    }else{
                        final Intent intent = new Intent(DisplayAllChars.this, userActivity.class);
                        intent.putExtra("name",name);
                        startActivity(intent);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        final RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new GridLayoutManager(this,4);

        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
        JSONObject json = null;
        try {
            //Waiting for the async to end. Otherwise we will get an empty arraylist.
           json = (JSONObject) getChamps.get();
           JSONObject Data = json.getJSONObject("data");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(json !=null) {
            try {
                this.myList = AsyncChampDatas.ExtractJSON(json.getJSONObject("data"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            System.err.println("Json null");
        }
        Log.i("Vide ???", myList.toString());
        mAdapter = new MyAdapter(myList,this);
        recyclerView.setAdapter(mAdapter);

    }

}

