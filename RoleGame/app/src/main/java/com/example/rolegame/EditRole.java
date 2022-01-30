package com.example.rolegame;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rolegame.Adapters.DialogAdapter;
import com.example.rolegame.Adapters.EditRoleAdapter;
import com.example.rolegame.Adapters.EditRoleAdapter2;
import com.example.rolegame.DataBase.RoleDataBaseHelper;
import com.example.rolegame.Objects.GlobalClass;
import com.example.rolegame.Objects.Mechanics;
import com.example.rolegame.Objects.Role;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EditRole extends AppCompatActivity {

    //variables
    Button save_button;
    TextView role_name;
    EditText description;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RoleDataBaseHelper myDB;
    EditRoleAdapter2 customAdapter2;

    ArrayList<Mechanics> mechanics; //The ArrayList that saves the mechanics in it.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_role);

        Intent getIntent = getIntent();
        Role role = (Role) getIntent.getSerializableExtra("currentRole");

        //getting the references
        recyclerView = findViewById(R.id.recyclerView);
        save_button = findViewById(R.id.save_button);
        role_name = findViewById(R.id.roleName);
        description = findViewById(R.id.description);

        role_name.setText(role.getName());
        if (role.getDescription() != "no description")
        {
            description.setText(role.getDescription());
        }

        //Adapter part
        layoutManager = new GridLayoutManager(EditRole.this, 2,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        customAdapter2 = new EditRoleAdapter2(EditRole.this, role);
        recyclerView.setAdapter(customAdapter2);

        //the onclick that saves the role after the user finishes.
        save_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ArrayList<Integer> positions = new ArrayList<>();
                ArrayList<TextView> textViews = customAdapter2.getTextViews();
                ArrayList<String> headMechanics = GlobalClass.getHeadMechanics();
                mechanics = GlobalClass.getMechanicsArrayList();
                ArrayList<Mechanics> roleMechanics = role.getMechanics();
                myDB = new RoleDataBaseHelper(EditRole.this);

                 for (int i = 0; i < textViews.size(); i++) {
                     if (!headMechanics.get(i).equals(textViews.get(i).getText().toString())) {
                         for (int j = 0; j < mechanics.size(); j++) {
                             if (mechanics.get(j).getName().equals(textViews.get(i).getText().toString())) {

                                 for (int r = 0; r < roleMechanics.size(); r++) {
                                     if (mechanics.get(j).getType() == roleMechanics.get(r).getType()) {
                                         roleMechanics.remove(r);
                                     }
                                 }
                                 positions.add(j);
                                 j = mechanics.size();
                                 j++;
                             }
                         }
                     }
                 }

                 if (role.getMechanics() != null) {
                     for (int i = 0; i < role.getMechanics().size(); i++) {
                         positions.add(roleMechanics.get(i).getId());
                     }
                 }

                 String positionsToDB = null;
                 if (positions.size() > 0) {
                     positionsToDB = GlobalClass.convertIntToString(positions);
                 }

                 myDB.updateMechanics(String.valueOf(role.getId()),positionsToDB);
                 myDB.updateDescription(String.valueOf(role.getId()),description.getText().toString());

                Intent intent = new Intent(EditRole.this, RoleManager.class);
                startActivity(intent);

                finish();
                myDB.close();
            }
        });
    }

    //adds the menu icons to the action bar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_role,menu);
        return true;
    }

    //sets an onClick on the appbar icons that transfers the user the wanted activity.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent getIntent = getIntent();
        Role role = (Role) getIntent.getSerializableExtra("currentRole");

        //Deletes the selected role
        if (item.getItemId() == R.id.delete) {
            AlertDialog.Builder alert = new AlertDialog.Builder(EditRole.this);
            alert.setTitle("Delete " + role.getName() + "?");
            alert.setMessage("Are you sure you want to delete the role: " + role.getName());
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    RoleDataBaseHelper myDB = new RoleDataBaseHelper(EditRole.this);
                    myDB.deleteOneRow(String.valueOf(role.getId()));
                    Intent intent = new Intent(EditRole.this, RoleManager.class);
                    startActivity(intent);

                    finish();
                    myDB.close();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.create().show();
        }
        if (item.getItemId() == R.id.reset) {
            AlertDialog.Builder alert = new AlertDialog.Builder(EditRole.this);
            alert.setTitle("Reset " + role.getName() + "?");
            alert.setMessage("Are you sure you want to reset to default the role: " + role.getName());
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    RoleDataBaseHelper myDB = new RoleDataBaseHelper(EditRole.this);
                    myDB.updateMechanics(String.valueOf(role.getId()),null);
                    myDB.updateDescription(String.valueOf(role.getId()),null);

                    Intent intent = new Intent(EditRole.this, RoleManager.class);
                    startActivity(intent);
                    finish();
                    myDB.close();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.create().show();
        }
        return false;
    }
}