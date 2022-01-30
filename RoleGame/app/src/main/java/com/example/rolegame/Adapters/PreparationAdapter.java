package com.example.rolegame.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolegame.EditProfile;
import com.example.rolegame.Objects.Profile;
import com.example.rolegame.Preparation;
import com.example.rolegame.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PreparationAdapter extends RecyclerView.Adapter<PreparationAdapter.MyViewHolder>{

    //variables
    private Context context;
    private ArrayList<Profile> profiles;

    public PreparationAdapter(Context context, ArrayList <Profile> profiles){
        this.context = context;
        this.profiles = profiles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_preparation,parent,false);
        return new PreparationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Profile p = (Profile) profiles.get(position);
        if (p.isChosen())
        {
            holder.name_txt.setText(String.valueOf(p.getName()));
            if (p.getProfileUri() == null)
            {
                holder.profile_img.setImageResource(R.drawable.nophoto);
            }
            else {
                holder.profile_img.setImageURI(Uri.parse(String.valueOf(p.getProfileUri())));
            }
        }
        holder.profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditProfile.class);
                intent.putExtra("profile", p);
                context.startActivity(intent);
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_txt = itemView.findViewById(R.id.name_txt);
            profile_img = itemView.findViewById(R.id.CIV);
        }
    }
}
