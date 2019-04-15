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

import java.util.concurrent.ExecutionException;

public class Masteries_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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

        for(int i=0;i<3;i++){
            try {
                JSONObject act = MasteriesArray.getJSONObject(i);
                setChampion(act,i);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


        }
        View view= inflater.inflate(R.layout.fragment_masteries_, container, false);
        return view;
    }

    private void setChampion(JSONObject act,int i) throws JSONException, ExecutionException, InterruptedException {
        ImageView img;
        TextView t;
        switch(i){
            case 1:
                img = ((userActivity)getActivity()).findViewById(R.id.ImageChampMasteries1);
                t = ((userActivity)getActivity()).findViewById(R.id.TextChampMasteries1);
                String ImgChampionURL = getURL(act.getString("championId"));
                t.setText("Niveau : " + act.getString("championLevel"));

                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    private String getURL(String championId) throws JSONException, ExecutionException, InterruptedException {
        String url ="";
        AsyncChampDatas req = new AsyncChampDatas();
        req.execute("http://ddragon.leagueoflegends.com/cdn/9.6.1/data/en_US/champion.json");
        JSONObject Data = null;
        JSONObject InfosChamps = null;
        JSONObject ChampsJSON = (JSONObject) req.get();
        Data = ChampsJSON.getJSONObject("data");
        Log.i("DATADATA", Data.toString());
            return null;
    }



}
