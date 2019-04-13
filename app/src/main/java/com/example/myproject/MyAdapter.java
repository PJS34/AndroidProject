package com.example.myproject;

import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<String> characters = Arrays.asList(
      "Salut",
            "Bonjour",
            "Hello",
            "Buenos Dias",
            "Hola senor",
            "Nihaw"

    );


    @Override
    public int getItemCount() {
        return characters.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell, parent, false);
        return new MyViewHolder(view);
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

            name = ((TextView) itemView.findViewById(R.id.name));


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(itemView.getContext())
                          .setTitle(currentPair)
                          .setMessage(currentPair)
                            .show();
                }
            });
        }

        public void display(String pair) {
            currentPair = pair;
            name.setText(pair);
            //description.setText(pair.second);
        }
    }
}
