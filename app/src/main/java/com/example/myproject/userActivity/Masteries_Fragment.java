package com.example.myproject.userActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myproject.Async_Api_request.AsyncChampDatas;
import com.example.myproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class Masteries_Fragment extends Fragment {
private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_masteries_, container, false);
        AsyncChampDatas req = new AsyncChampDatas();
        // Read xml file and return View object.

        req.execute("https://euw1.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/" +
                        ((userActivity)getActivity()).summonerID+"?api_key="+ userActivity.APIKey);
        // inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot)
        JSONArray MasteriesArray = null;
        try {
            MasteriesArray = (JSONArray) req.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GridLayout MyLayout = ((userActivity)getActivity()).findViewById(R.id.MasteriesLayout);
        int cpt =0;
        for(int i=1;i<4;i++){
            try {
                JSONObject act = MasteriesArray.getJSONObject(i);
                System.out.println("JE LANCE"+cpt);
                setChampion(act,i);
                cpt++;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


        }

        return view;
    }

    private void setChampion(JSONObject act,int i) throws JSONException, ExecutionException, InterruptedException {
        System.out.println("HOP LA JE PASSE");
        ImageView img;
        TextView t;
        String ImgChampionURL;
        switch(i){
            case 1:
                img = view.findViewById(R.id.ImageChampMasteries1);
                //t = view.findViewById(R.id.TextChampMasteries1);
                ImgChampionURL = getURL(act.getString("championId"));
                System.out.println(ImgChampionURL);
                Glide.with(this)
                        .load(ImgChampionURL)
                        .into(img);
               // t.setText("Niveau : " + act.getString("championLevel"));

                break;
            case 2:
                img = view.findViewById(R.id.ImageChampMasteries2);
              //  t = view.findViewById(R.id.TextChampMasteries2);
                ImgChampionURL = getURL(act.getString("championId"));
                System.out.println(ImgChampionURL);
                Glide.with(this)
                        .load(ImgChampionURL)
                        .into(img);
                //t.setText("Niveau : " + act.getString("championLevel"));
                break;
            case 3:
                img = view.findViewById(R.id.ImageChampMasteries3);
                //t = view.findViewById(R.id.TextChampMasteries3);
                ImgChampionURL = getURL(act.getString("championId"));
                System.out.println(ImgChampionURL);
                Glide.with(this)
                        .load(ImgChampionURL)
                        .into(img);
                //t.setText("Niveau : " + act.getString("championLevel"));
                break;
        }
    }

    private String getURL(String championId) throws JSONException, ExecutionException, InterruptedException {
        String url ="";
        String Champname="";
        AsyncChampDatas req = new AsyncChampDatas();
        req.execute("http://ddragon.leagueoflegends.com/cdn/9.6.1/data/en_US/champion.json");
        JSONObject Data = null;
        JSONObject InfosChamps = null;
        JSONObject ChampsJSON = (JSONObject) req.get();
        Data = ChampsJSON.getJSONObject("data");
        Log.i("DATADATA", Data.toString());
        for (Iterator iterator = Data.keys(); iterator.hasNext();) {
            String Champ = (String) iterator.next();
            JSONObject act = Data.getJSONObject(Champ);
           // Log.i("ICI",act.toString());
            if(act.getString("key").equals(championId)){
                Log.i("WINNER",Champ);
                Champname = Champ;
            }
        }
            return "http://ddragon.leagueoflegends.com/cdn/9.7.1/img/champion/"+ Champname + ".png";
    }



}
