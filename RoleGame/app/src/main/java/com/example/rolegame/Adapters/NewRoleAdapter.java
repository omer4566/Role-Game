package com.example.rolegame.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolegame.Objects.Ability;
import com.example.rolegame.Objects.GlobalClass;
import com.example.rolegame.R;

import java.util.ArrayList;

public class NewRoleAdapter extends RecyclerView.Adapter<NewRoleAdapter.MyViewHolder>{

    //variables
    private Context context;
    private ArrayList<Ability> abilities = GlobalClass.getArrayList();

    public NewRoleAdapter(Context context)
    {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_new_role,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        abilities.get(position).setChecked(false);
        holder.abilityName.setText(abilities.get(position).getName());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String active = "?";
                if (abilities.get(position).isActive()) {
                    active = "Active";
                }
                else {
                    active = "Passive";
                }
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("[" + abilities.get(position).getName() + "] " + " - " + active);
                alert.setMessage(abilities.get(position).getDescription());
                alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.create().show();
            }
        });

        holder.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    abilities.get(position).setChecked(true);
                }
                else{
                    abilities.get(position).setChecked(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return abilities.size();
    }

    public ArrayList<Ability> getAbilities() {

        ArrayList<Ability> newAbilities = new ArrayList<>();

        for (int i = 0; i < abilities.size();i++)
        {
            if (abilities.get(i).isChecked())
            {
                newAbilities.add(abilities.get(i));
            }
        }
        return newAbilities;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView abilityName;
        SwitchCompat switchCompat;
        ConstraintLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            abilityName = itemView.findViewById(R.id.ability_name);
            switchCompat = itemView.findViewById(R.id.switchCompat);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
