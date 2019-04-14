package com.example.myproject;

import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<String> characters;

    public MyAdapter() {
        characters = new ArrayList<>();
        characters.add("rignrfnb");
        characters.add("bla");
        characters.add("coucou");
    }

    public MyAdapter(ArrayList myList) {
        characters = myList;
        Log.i("MyList",characters.toString());
    }


    @Override
    public int getItemCount() {
        return characters.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell, parent, false);
        return new MyViewHolder(view);
        //return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String pair = characters.get(position);
        holder.display(pair);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private String currentPair;

        public MyViewHolder(final View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);

            /*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(itemView.getContext())
                          .setTitle(currentPair)
                          .setMessage(currentPair)
                            .show();
                }
            });
            */
        }

        public void display(String pair) {
            this.currentPair = pair;
            name.setText(pair);
            //description.setText(pair.second);
        }
    }
}
