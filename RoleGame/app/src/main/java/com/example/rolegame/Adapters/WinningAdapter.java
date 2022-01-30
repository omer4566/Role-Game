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

public class WinningAdapter extends RecyclerView.Adapter<WinningAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Player> players;

    public WinningAdapter (Context context, ArrayList<Player> players) {
        this.context = context;
        this.players = players;
    }

    @NonNull
    @Override
    public WinningAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_preparation,parent,false);
        return new WinningAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WinningAdapter.MyViewHolder holder, int position) {
        holder.name_txt.setText(players.get(position).getProfile().getName());
        if (players.get(position).getProfile().getProfileUri() == null)
        {
            holder.profile_img.setImageResource(R.drawable.nophoto);
        }
        else
        {
            holder.profile_img.setImageURI(Uri.parse(players.get(position).getProfile().getProfileUri()));
        }
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
