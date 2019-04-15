package com.example.myproject.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.support.v7.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.myproject.Detailled_Champion.Display_One_Champion;
import com.example.myproject.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Activity Myactivity;
    private List<String> characters;


    public MyAdapter(ArrayList myList,Activity A) {
        characters = myList;
        this.Myactivity = A;
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
        return new MyViewHolder(view,Myactivity);
        //return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String pair = characters.get(position);
        holder.display(pair);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageButton name;
        private String currentPair;
        private Activity myActivity;
        public MyViewHolder(final View itemView,Activity A) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            this.myActivity=A;
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    launchDetailledChampionActivity();
                    Log.i("Touched",currentPair);
                }
            });

        }
        //launching detailled informations on one champion
        private void launchDetailledChampionActivity() {
            final Intent intent = new Intent(myActivity,Display_One_Champion.class);
            intent.putExtra("Champion",currentPair);
            myActivity.startActivity(intent);

        }

        //Make the UI of every element
        public void display(String pair) {
            this.currentPair = pair;
            Glide.with(Myactivity)
                    .load("http://ddragon.leagueoflegends.com/cdn/9.7.1/img/champion/"+ pair + ".png")
                    .into(name);

        }
    }
}
