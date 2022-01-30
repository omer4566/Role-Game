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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolegame.Objects.Ability;
import com.example.rolegame.Objects.GlobalClass;
import com.example.rolegame.Objects.Mechanics;
import com.example.rolegame.Objects.Role;
import com.example.rolegame.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class EditRoleAdapter2 extends RecyclerView.Adapter<EditRoleAdapter2.MyViewHolder> {

    //variables
    private Context context;
    private Role role;
    private ArrayList<Mechanics> roleMechanics;
    private ArrayList<String> headMechanics = GlobalClass.getHeadMechanics();
    RecyclerView bottom_recyclerView;
    RecyclerView.LayoutManager layoutManager;
    EditRoleAdapter customAdapter;
    private ArrayList<TextView> textViews;

    public EditRoleAdapter2(Context context, Role role)
    {
        this.context = context;
        this.role = role;
        roleMechanics = role.getMechanics();
        textViews = new ArrayList<>();

        if (roleMechanics != null) {
            for (int i = 0; i < roleMechanics.size(); i++) {
                headMechanics.set(roleMechanics.get(i).getType() -1, roleMechanics.get(i).getName());
            }
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_edit_role,parent,false);
        return new EditRoleAdapter2.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditRoleAdapter2.MyViewHolder holder, int position) {

        textViews.add(holder.textView);

        holder.textView.setText(headMechanics.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                View bottomSheetView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.dialog_bottom,null);

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

                bottom_recyclerView = bottomSheetDialog.findViewById(R.id.bottom_recyclerView);
                layoutManager = new GridLayoutManager(bottomSheetDialog.getContext(), 3,RecyclerView.VERTICAL,false);

                //Setting up the dialog custom adapter
                bottom_recyclerView.setLayoutManager(layoutManager);

                customAdapter = new EditRoleAdapter(context,bottomSheetDialog, position + 1, role, holder.textView);
                bottom_recyclerView.setAdapter(customAdapter);
            }
        });
    }

    @Override
    public int getItemCount() {
        return headMechanics.size();
    }

    public ArrayList<TextView> getTextViews () {
        return textViews;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        //SwitchCompat switchCompat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            //switchCompat = itemView.findViewById(R.id.switchCompat);
        }
    }
}
