package com.example.rolegame.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolegame.Objects.Profile;
import com.example.rolegame.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.MyViewHolder>  {

    //variables
    private Context context;
    private ArrayList <Profile> profiles;

    public ProfileListAdapter(Context context, ArrayList <Profile> profiles){
        this.context = context;
        this.profiles = profiles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_profilelist,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final boolean[] isChosen = new boolean[1];
        final Profile p = (Profile) profiles.get(position);
        holder.name_txt.setText(String.valueOf(p.getName()));

        if (p.getProfileUri() == null)
        {
            holder.profile_img.setImageResource(R.drawable.nophoto);
        }
        else {
            holder.profile_img.setImageURI(Uri.parse(String.valueOf(p.getProfileUri())));
        }

        if (p.isChosen())
        {
            p.setBool(true);
            isChosen[0] = true;
            holder.profile_img.setBorderColor(Color.GREEN);
        }
        else
        {
            p.setBool(false);
            isChosen[0] = false;
            holder.profile_img.setBorderColor(Color.RED);
        }

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isChosen[0]) {
                    p.setBool(false);
                    isChosen[0] = false;
                    holder.profile_img.setBorderColor(Color.RED);
                }
                else {
                    p.setBool(true);
                    isChosen[0] = true;
                    holder.profile_img.setBorderColor(Color.GREEN);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_txt;
        CircleImageView profile_img;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_txt = itemView.findViewById(R.id.name_txt);
            profile_img = itemView.findViewById(R.id.CIV);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
