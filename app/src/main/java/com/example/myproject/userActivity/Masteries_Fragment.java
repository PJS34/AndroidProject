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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myproject.Async_Api_request.AsyncChampDatas;
import com.example.myproject.Detailled_Champion.Display_One_Champion;
import com.example.myproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class Masteries_Fragment extends Fragment {
private View view;
    private String ImgChampionURL;

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

    private void setChampion(final JSONObject act, int i) throws JSONException, ExecutionException, InterruptedException {
        System.out.println("HOP LA JE PASSE");
        ImageView img;
        TextView t;

        switch(i){
            case 1:
                img = view.findViewById(R.id.ImageChampMasteries1);
                //t = view.findViewById(R.id.TextChampMasteries1);

                this.ImgChampionURL = ((userActivity)getActivity()).getURL(act.getString("championId"));
                System.out.println(ImgChampionURL);
                Glide.with(this)
                        .load(ImgChampionURL)
                        .into(img);
                final String name = act.getString("championLevel");
               // t.setText("Niveau : " + act.getString("championLevel"));
                img.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getActivity(),"Niveau : " + name,Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 2:
                img = view.findViewById(R.id.ImageChampMasteries2);
              //  t = view.findViewById(R.id.TextChampMasteries2);
                this.ImgChampionURL = ((userActivity)getActivity()).getURL(act.getString("championId"));
                System.out.println(ImgChampionURL);
                Glide.with(this)
                        .load(ImgChampionURL)
                        .into(img);
                final String name2 = act.getString("championLevel");
                //t.setText("Niveau : " + act.getString("championLevel"));
                img.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getActivity(),"Niveau : " +name2,Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 3:
                img = view.findViewById(R.id.ImageChampMasteries3);
                //t = view.findViewById(R.id.TextChampMasteries3);
                ImgChampionURL = ((userActivity)getActivity()).getURL(act.getString("championId"));
                System.out.println(ImgChampionURL);
                Glide.with(this)
                        .load(ImgChampionURL)
                        .into(img);
                final String name3 = act.getString("championLevel");

                //t.setText("Niveau : " + act.getString("championLevel"));
                img.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getActivity(),"Niveau : " +name3,Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }

    }





}
