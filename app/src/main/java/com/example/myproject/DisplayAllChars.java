package com.example.myproject;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DisplayAllChars extends Activity {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_chars);
        AsyncChampDatas getChamps = new AsyncChampDatas();
         getChamps.execute();


        final RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new GridLayoutManager(this,4);
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
        try {
            //Waiting for the async to end. Otherwise we will get an empty arraylist.
            getChamps.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList myList =  getChamps.getList();
        Log.i("Vide ???", myList.toString());
        mAdapter = new MyAdapter(myList);
        recyclerView.setAdapter(mAdapter);

    }
}

