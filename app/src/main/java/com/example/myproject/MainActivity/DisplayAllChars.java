package com.example.myproject.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.myproject.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.example.myproject.Async_Api_request.AsyncChampDatas;

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
        recyclerView.setHasFixedSize(false);
        // use a linear layout manager
        layoutManager = new GridLayoutManager(this,4);

        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
        try {
            //Waiting for the async to end. Otherwise we will get an empty arraylist.
            getChamps.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ArrayList myList =  getChamps.getList();
        Log.i("Vide ???", myList.toString());
        mAdapter = new MyAdapter(myList,this);
        recyclerView.setAdapter(mAdapter);

    }
}

