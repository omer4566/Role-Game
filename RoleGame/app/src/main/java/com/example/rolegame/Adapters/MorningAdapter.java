package com.example.rolegame.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolegame.Objects.Player;
import com.example.rolegame.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MorningAdapter extends RecyclerView.Adapter<MorningAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> txt;
    ArrayList<Player> players;

    public MorningAdapter (Context context, ArrayList<String> txt, ArrayList<Player> players)
    {
        this.context = context;
        this.txt = txt;
        this.players = players;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_morning,parent,false);
        return new MorningAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name_txt.setText(players.get(position).getProfile().getName());
        if (players.get(position).getProfile().getProfileUri() == null)
        {
            holder.profile_img.setImageResource(R.drawable.nophoto);
        }
        else
        {
            holder.profile_img.setImageURI(Uri.parse(players.get(position).getProfile().getProfileUri()));
        }
        holder.morning_txt.setText(txt.get(position));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_txt, morning_txt;
        CircleImageView profile_img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name_txt = itemView.findViewById(R.id.name_txt);
            morning_txt = itemView.findViewById(R.id.morning_txt);
            profile_img = itemView.findViewById(R.id.CIV);
        }
    }
}
