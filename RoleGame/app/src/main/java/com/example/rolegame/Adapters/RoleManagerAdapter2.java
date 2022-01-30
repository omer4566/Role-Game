package com.example.rolegame.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolegame.EditRole;
import com.example.rolegame.Objects.GlobalClass;
import com.example.rolegame.Objects.Role;
import com.example.rolegame.R;
import com.example.rolegame.RoleManager;

import java.util.ArrayList;

public class RoleManagerAdapter2 extends RecyclerView.Adapter<RoleManagerAdapter2.MyViewHolder>{

    //variables
    private Context context;
    private ArrayList<Role> roles;
    private ArrayList<Integer> roleImgList = GlobalClass.getImages();
    private Activity activity;
    private int playersAmount;
    private int [] number;

    public RoleManagerAdapter2 (Context context, ArrayList<Role> roles, int playersAmount ) {
        this.context = context;
        this.roles = roles;
        this.playersAmount = playersAmount;
        this.activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_role_manager2,parent,false);
        return new RoleManagerAdapter2.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(roles.get(position).getName());
        holder.imageView.setImageResource(roleImgList.get(roles.get(position).getImagePosition()));

        number = new int[roles.size()];
        number[position] = 0;

        holder.textView2.setText(""+ number[position]);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playersAmount > getTotalNum())
                {
                    number[position]++;
                    holder.textView2.setText(""+ number[position]);
                }
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number[position] > 0)
                {
                    number[position]--;
                    holder.textView2.setText(""+ number[position]);
                }
            }
        });

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String role_abilities = "Abilities: ", role_mechanics = "Mechanics: ";

                if (roles.get(position).getAbilities() != null)
                {
                    for (int i = 0; i < roles.get(position).getAbilities().length; i++)
                    {
                        role_abilities = role_abilities + roles.get(position).getAbilities()[i].getName() + ", ";
                    }
                }

                if (roles.get(position).getMechanics() != null)
                {
                    for (int i = 0; i < roles.get(position).getMechanics().size(); i++)
                    {
                        role_mechanics = role_mechanics + roles.get(position).getMechanics().get(i).getName() + ", ";
                    }
                }

                LoadDialog loadDialog = new LoadDialog(activity);
                loadDialog.startLoadingDialog(roles.get(position).getName(),role_abilities,role_mechanics, roles.get(position).getDescription(), roles.get(position));
            }
        });
    }

    public int getTotalNum() {
        int count = 0;
        for (int i = 0; i < number.length; i++)
        {
            count += number[i];
        }
        return count;
    }

    @Override
    public int getItemCount() {
        return roles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, remove, add;
        TextView textView, textView2;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.roleImage);
            textView = itemView.findViewById(R.id.roleName);
            textView2 = itemView.findViewById(R.id.number);
            add = itemView.findViewById(R.id.add_button);
            remove = itemView.findViewById(R.id.remove_button);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}