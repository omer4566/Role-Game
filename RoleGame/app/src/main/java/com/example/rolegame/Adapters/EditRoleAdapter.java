package com.example.rolegame.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolegame.EditRole;
import com.example.rolegame.Objects.GlobalClass;
import com.example.rolegame.Objects.Mechanics;
import com.example.rolegame.Objects.Role;
import com.example.rolegame.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class EditRoleAdapter extends RecyclerView.Adapter<EditRoleAdapter.MyViewHolder> {

    //variables
    private int type;
    private Context context;
    private BottomSheetDialog dialog;
    private Role role;
    private ArrayList<Mechanics> mechanics = GlobalClass.getMechanicsArrayList();
    private ArrayList<Mechanics> newMechanics = new ArrayList<>();
    private TextView textView;

    public EditRoleAdapter (Context context, BottomSheetDialog dialog, int type, Role role, TextView textView)
    {
        this.context = context;
        this.role = role;
        this.dialog = dialog;
        this.type = type;
        this.textView = textView;

        for (int i = 0; i < mechanics.size(); i++) {
            if (mechanics.get(i).getType() == type) {
                newMechanics.add(mechanics.get(i));
            }
        }
    }

    @NonNull
    @Override
    public EditRoleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_edit_role,parent,false);
        return new EditRoleAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditRoleAdapter.MyViewHolder holder, int position) {

        holder.textView.setText(newMechanics.get(position).getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("[" + newMechanics.get(position).getName() + "] " + "Description");
                alert.setMessage(newMechanics.get(position).getDescription());
                alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.setPositiveButton("Use", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        if (role.getMechanics() == null) {
                            ArrayList<Mechanics> mechanics = new ArrayList<>();
                            role.setMechanics(mechanics);
                        }
                        role.addMechanic(newMechanics.get(position));
                        textView.setText(newMechanics.get(position).getName());
                    }
                });
                alert.create().show();
                /*dialog.cancel();
                pos = position;*/
            }
        });
    }

    @Override
    public int getItemCount() {

        return newMechanics.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
