package com.example.rolegame.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolegame.DataBase.RoleDataBaseHelper;
import com.example.rolegame.EditRole;
import com.example.rolegame.Objects.GlobalClass;
import com.example.rolegame.Objects.Role;
import com.example.rolegame.R;
import com.example.rolegame.RoleManager;

import java.util.ArrayList;
import java.util.Arrays;

public class RoleManagerAdapter extends RecyclerView.Adapter<RoleManagerAdapter.MyViewHolder>{

    //variables
    private Context context;
    private ArrayList<Role> roles;
    private ArrayList<Integer> roleImgList = GlobalClass.getImages();
    private Activity activity;

    public RoleManagerAdapter (Context context, ArrayList<Role> roles ) {
        this.context = context;
        this.roles = roles;
        this.activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_role_manager,parent,false);
        return new RoleManagerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(roles.get(position).getName());
        holder.imageView.setImageResource(roleImgList.get(roles.get(position).getImagePosition()));

        RoleDataBaseHelper myDB = new RoleDataBaseHelper(context);

        holder.switchCompat.setChecked(roles.get(position).isChosen());

        holder.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    roles.get(position).setChosen(true);
                    myDB.updateIsChosen(String.valueOf(roles.get(position).getId()),true);
                }
                else
                {
                    roles.get(position).setChosen(false);
                    myDB.updateIsChosen(String.valueOf(roles.get(position).getId()),false);
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

    @Override
    public int getItemCount() {
        return roles.size();
    }

    public ArrayList<Role> getUpdated()
    {
        return roles;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        SwitchCompat switchCompat;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.roleImage);
            textView = itemView.findViewById(R.id.roleName);
            switchCompat = itemView.findViewById(R.id.switchCompat);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
