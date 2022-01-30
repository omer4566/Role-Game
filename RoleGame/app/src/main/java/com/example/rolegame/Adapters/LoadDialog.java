package com.example.rolegame.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rolegame.EditRole;
import com.example.rolegame.Objects.Role;
import com.example.rolegame.R;

public class LoadDialog {

    private Activity activity;
    private AlertDialog dialog;
    private TextView name, abilities, mechanics, description;
    private Button button;

    //Loads the builder with the activity chosen.
    LoadDialog(Activity myActivity){
        activity = myActivity;
    }

    //The dialog builder
    void startLoadingDialog(String role_name, String role_abilities, String role_mechanics, String role_description, Role role){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.role_info_dialog,null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();

        name = dialog.findViewById(R.id.roleName);
        abilities = dialog.findViewById(R.id.roleAbilities);
        mechanics = dialog.findViewById(R.id.roleMechanics);
        description = dialog.findViewById(R.id.roleDescription);
        button = dialog.findViewById(R.id.edit_button);

        String team = "Team";
        if (role.getTeam() == 1)
            team = "Good";
        else if (role.getTeam() == 2)
            team = "Solo";
        else if (role.getTeam() == 3)
            team = "Evil";

        name.setText(role_name + " - " + team);
        abilities.setText(role_abilities);
        mechanics.setText(role_mechanics);
        description.setText(role_description);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EditRole.class);
                intent.putExtra("currentRole",role);
                activity.startActivity(intent);
                dismissDialog();
            }
        });

    }

    void dismissDialog(){
        dialog.dismiss();
    }
}
