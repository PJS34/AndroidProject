package com.example.myproject.userActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myproject.Async_Api_request.AsyncChampDatas;
import com.example.myproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class Actual_Game_Fragment extends Fragment {
    private View view = null;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Read xml file and return View object.

        // inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot)

        this.view= inflater.inflate(R.layout.fragment_actual__game_, container, false);



        AsyncChampDatas getMatchDatas = new AsyncChampDatas();
        String url = "https://euw1.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/" + ((userActivity)getActivity()).summonerID+ "?api_key=" +userActivity.APIKey;
        getMatchDatas.execute(url);

        TextView v =view.findViewById(R.id.inGame);
        //System.out.println(((userActivity)getActivity()).findViewById(R.id.EnemyTeam).toString());
        //Log.i("vcreated ?",v==null?"null":"Pasnul");
        try {
            System.out.println(url);
            JSONObject gameData = (JSONObject) getMatchDatas.get();
           // Log.i("v ?",v==null?"null":"Pasnul");
            if(gameData==null){
                v.setText("Not currently in game");
                TextView t1 = view.findViewById(R.id.textView4);
                TextView t2 = view.findViewById(R.id.textView5);
                t1.setVisibility(View.INVISIBLE);
                t2.setVisibility(View.INVISIBLE);
            }else {
               // Log.i("v ?",v==null?"null":"Pasnul");
                v.setText("Actual Game : ");
                TextView t1 = view.findViewById(R.id.textView4);
                TextView t2 = view.findViewById(R.id.textView5);
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.VISIBLE);
                Log.i("GameData", gameData.toString());
                SetImages(gameData);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void SetImages(JSONObject gameData) throws JSONException, ExecutionException, InterruptedException {
        JSONArray AllPlayers = gameData.getJSONArray("participants");
        LinearLayout MyTeam = view.findViewById(R.id.MyTeam);
        LinearLayout EnemyTeam = view.findViewById(R.id.EnemyTeam);

        for(int i=0;i<AllPlayers.length();i++){
            JSONObject ActualPlayer= AllPlayers.getJSONObject(i);

            if(ActualPlayer.getInt("teamId") == 200){

                SetPlayerImage(MyTeam, ActualPlayer);
            }else if (ActualPlayer.getInt("teamId") == 100){
                SetPlayerImage(EnemyTeam, ActualPlayer);
            }
            Log.i("Player",ActualPlayer.toString());
        }
    }

    private void SetPlayerImage(LinearLayout myTeam, JSONObject actualPlayer) throws JSONException, ExecutionException, InterruptedException {
        ImageButton myView1 = new ImageButton(getActivity());
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(200 ,200);
        myView1.setLayoutParams(parms);
        //myView1.requestLayout();
        System.out.println(((userActivity)getActivity()).getURL(actualPlayer.getString("championId")));
        Glide.with(this)
                .load(((userActivity)getActivity()).getURL(actualPlayer.getString("championId")))
                .into(myView1);
        myTeam.addView(myView1);
        final String namePlayer = actualPlayer.getString("summonerName");
        myView1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),namePlayer,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
