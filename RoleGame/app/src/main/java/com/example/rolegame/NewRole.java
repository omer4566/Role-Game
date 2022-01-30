package com.example.rolegame;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rolegame.Adapters.DialogAdapter;
import com.example.rolegame.Adapters.NewRoleAdapter;
import com.example.rolegame.Adapters.ProfileListAdapter;
import com.example.rolegame.DataBase.RoleDataBaseHelper;
import com.example.rolegame.Objects.Ability;
import com.example.rolegame.Objects.GlobalClass;
import com.example.rolegame.Objects.Profile;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class NewRole extends AppCompatActivity {

    //variables
    Button save_button;
    ImageView wallpaper_button;
    EditText name;

    RecyclerView bottom_recyclerView, recyclerView;
    RecyclerView.LayoutManager layoutManager ,layoutManager2;
    DialogAdapter customAdapter;
    NewRoleAdapter customAdapter2;

    private ArrayList<Ability> abilities;

    int team = 2;
    Button good, nut, bad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_role);

        //getting the references
        name = findViewById(R.id.enterPersonName);
        save_button = findViewById(R.id.save_button);
        wallpaper_button = findViewById(R.id.wallpaper_button);
        recyclerView = findViewById(R.id.recyclerView);
        good = findViewById(R.id.button1);
        nut = findViewById(R.id.button2);
        bad = findViewById(R.id.button3);

        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team = 1;
            }
        });
        nut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team = 2;
            }
        });
        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team = 3;
            }
        });

        //when clicking the image button a dialog will appear, the dialog has images in it that
        //the user can choose to use as the role image.
        wallpaper_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(NewRole.this);
                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_bottom,null);

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

                bottom_recyclerView = bottomSheetDialog.findViewById(R.id.bottom_recyclerView);

                layoutManager = new GridLayoutManager(bottomSheetDialog.getContext(), 4,RecyclerView.VERTICAL,false);

                //Setting up the dialog custom adapter
                bottom_recyclerView.setLayoutManager(layoutManager);

                customAdapter = new DialogAdapter(NewRole.this,bottomSheetDialog,wallpaper_button);
                bottom_recyclerView.setAdapter(customAdapter);
            }
        });

        //the second custom adapter (abilities)
        layoutManager2 = new GridLayoutManager(this, 2,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager2);

        customAdapter2 = new NewRoleAdapter(NewRole.this);
        recyclerView.setAdapter(customAdapter2);

        save_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                abilities = customAdapter2.getAbilities();

                ArrayList<Integer> positions = new ArrayList<>();
                ArrayList<Ability> activeAbilities = new ArrayList<>();

                for (int i = 0; i < abilities.size();i++)
                {
                    if (!abilities.get(i).isActive())
                    {
                        activeAbilities.add(abilities.get(i));
                    }
                }

                //if true there are more then 1 compatible abilities chosen.
                if (activeAbilities.size() > 3)
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(NewRole.this);
                    alert.setTitle("Issue Found");
                    alert.setMessage("You can't choose more then 3 Active abilities.");
                    alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert.create().show();
                }
                else
                {
                    //Checks if the user entered a name or not.
                    String n = name.getText().toString();
                    if (n.matches(""))
                    {
                        Toast.makeText(NewRole.this, "Name is Empty", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //continue the role creation process
                        RoleDataBaseHelper myDB = new RoleDataBaseHelper(NewRole.this);

                        //takes the positions and turns them into a string
                        for (int i = 0; i < abilities.size();i++) {
                            if (abilities.get(i).isChecked()) {
                                positions.add(abilities.get(i).getId());
                            }
                        }
                        String positionsToDB = null;
                        if (positions.size() > 0) {
                            positionsToDB = GlobalClass.convertIntToString(positions);
                        }

                        //getting the image
                        int position = 0;
                        if (customAdapter != null) {
                            position = customAdapter.getImagePosition();
                        }

                        //adds the role to the database.
                        myDB.addRole(name.getText().toString().trim(), team, positionsToDB, null ,position,true);

                        Intent intent = new Intent(NewRole.this, RoleManager.class);
                        setResult(Activity.RESULT_OK,intent);

                        //Finishes the activity and plays the closing animation.
                        finish();
                    }
                }
            }
        });
    }

    //on clicking the only options menu button this function will send the user back to the
    //role manager activity.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //setting up an intent
        Intent send = new Intent(NewRole.this, RoleManager.class);
        startActivity(send);
        finish();
        return true;
    }
}