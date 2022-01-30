package com.example.rolegame.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolegame.Objects.Player;
import com.example.rolegame.R;
import com.google.android.material.transition.Hold;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Player> players;
    private ArrayList<Player> alivePlayers;
    private Button finish;
    private int gray;
    private int lastPos = -1;
    private boolean[] isChecked;

    public GameAdapter (Context context, ArrayList<Player> players, ArrayList<Player> alivePlayers, Button finish)
    {
        this.context = context;
        this.players = players;
        this.alivePlayers = alivePlayers;
        this.finish = finish;
        gray = context.getResources().getColor(R.color.gray);
        isChecked = new boolean[alivePlayers.size()];
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_preparation,parent,false);
        return new GameAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        isChecked[position] = false;

        holder.profile_img.setBorderColor(gray);
        holder.name_txt.setText(alivePlayers.get(position).getProfile().getName());
        if (alivePlayers.get(position).getProfile().getProfileUri() == null)
        {
            holder.profile_img.setImageResource(R.drawable.nophoto);
        }
        else
        {
            holder.profile_img.setImageURI(Uri.parse(alivePlayers.get(position).getProfile().getProfileUri()));
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isChecked[position]) {
                    holder.profile_img.setBorderColor(context.getResources().getColor(R.color.design_default_color_secondary_variant));
                    isChecked[position] = true;
                    finish.setText("finish turn");
                    if (lastPos != position)
                    {
                        notifyItemChanged(lastPos);
                    }
                    lastPos = position;
                }
                else
                {
                    finish.setText("skip turn");
                    holder.profile_img.setBorderColor(gray);
                    isChecked[position] = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return alivePlayers.size();
    }

    public ArrayList<Integer> getChosenPlayers() {
        ArrayList<Integer> chosenPlayers = new ArrayList<>();
        for (int i = 0; i < isChecked.length; i++)
        {
            if (isChecked[i]) {
                chosenPlayers.add(i);
            }
        }
        return chosenPlayers;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_txt;
        CircleImageView profile_img;
        ConstraintLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            name_txt = itemView.findViewById(R.id.name_txt);
            profile_img = itemView.findViewById(R.id.CIV);
        }
    }
}
