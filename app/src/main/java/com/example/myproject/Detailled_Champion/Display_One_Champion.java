package com.example.myproject.Detailled_Champion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.myproject.Async_Api_request.AsyncChampDatas;
import com.example.myproject.MainActivity.DisplayAllChars;
import com.example.myproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Display_One_Champion extends Activity {
    private String Champion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__one__champion);
        Intent intent = getIntent();
        Champion = intent.getStringExtra("Champion");

        final ConstraintLayout myMainLayout = (ConstraintLayout) findViewById(R.id.One_Champ_mainLayout);
       String url = "http://ddragon.leagueoflegends.com/cdn/img/champion/loading/" + Champion +"_0.jpg";
       Log.i("URL",url);
        Glide.with(this)
                .load(url)
                .into(new SimpleTarget<Drawable>() {

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        myMainLayout.setBackground(resource);
                    }
                });


        AsyncChampDatas req = new AsyncChampDatas();
        req.execute("http://ddragon.leagueoflegends.com/cdn/9.7.1/data/en_US/champion/"+ Champion +".json");
        //We set here the informations with the informations we already have
        setBasicsInformations();
        JSONObject Data = null;
        JSONObject InfosChamps = null;
        try {
            JSONObject ChampsJSON = req.get();
             Data = ChampsJSON.getJSONObject("data");

            InfosChamps = Data.getJSONObject(this.Champion);
            Log.i("DATACHAMP",InfosChamps.toString());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            System.err.println("There is no data object");
            e.printStackTrace();
        }

        TextView Title = (TextView) findViewById(R.id.title);
        TextView lore = findViewById(R.id.Lore);
        try {
            this.setStringInformation(InfosChamps,"title",Title);
            this.setStringInformation(InfosChamps,"lore",lore);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            SetSpellsGrid(InfosChamps);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void SetSpellsGrid(JSONObject infosChamps) throws JSONException {
        setPassive(infosChamps.getJSONObject("passive"));
        JSONArray Spells = infosChamps.getJSONArray("spells");
        String[] tab ={"Q","W","E","R"};

        for (int i = 0; i<Spells.length(); i++) {
            JSONObject elem = Spells.getJSONObject(i);
            Log.i("Spells",elem.toString());

            try {
                setSpell(elem.getJSONObject("image").getString("full"),tab[i],elem.getString("description"),elem.getString("name"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void setPassive(JSONObject passive) throws JSONException {

        JSONObject ImageJson = passive.getJSONObject("image");
        String description = passive.getString("description");
        String name = passive.getString("name");
        String urlPassive = ImageJson.getString("full");
        Log.i("URLImage",urlPassive);
        try {
            setSpell(urlPassive,"Passive",description,name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSpell(String urlPassive, String spellid, String description, final String name) throws Exception {
        ImageButton ActualImageButton = null;
        TextView Actualdesc=null;
        switch (spellid){
            case "Passive":
                ActualImageButton = findViewById(R.id.Passive);
                Actualdesc = findViewById(R.id.PassiveDesc);
                break;
            case "Q":
                ActualImageButton = findViewById(R.id.QSpell);
                Actualdesc = findViewById(R.id.QSpellDesc);
                break;
            case"W":
                ActualImageButton = findViewById(R.id.WSpell);
                Actualdesc = findViewById(R.id.WSpellDesc);
                break;
            case "E":
                ActualImageButton = findViewById(R.id.ESpell);
                Actualdesc = findViewById(R.id.ESpellDesc);
                break;
            case "R":
                ActualImageButton = findViewById(R.id.RSpell);
                Actualdesc = findViewById(R.id.RSpellDesc);
                break;
        }
        if(spellid.equals("Passive")){
            Glide.with(this)
                    .load(new URL("http://ddragon.leagueoflegends.com/cdn/9.7.1/img/passive/" + urlPassive) )
                    .into(ActualImageButton);
        }else {
            Glide.with(this)
                    .load("http://ddragon.leagueoflegends.com/cdn/9.7.1/img/spell/" + urlPassive)
                    .into(ActualImageButton);
        }
            Actualdesc.setText(description);
            ActualImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Display_One_Champion.this,name,Toast.LENGTH_SHORT).show();
                System.out.println("bla");
            }
        });

    }

    private void setStringInformation(JSONObject json,String key,TextView T) throws JSONException {
        String s = json.getString(key);
        T.setText(s);
    }

    private void setBasicsInformations() {
        TextView t = (TextView) findViewById(R.id.Champ_name);
        t.setText(Champion);

    }
}
