package com.example.rolegame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rolegame.Adapters.PreparationAdapter;
import com.example.rolegame.DataBase.ProfileDataBaseHelper;
import com.example.rolegame.Objects.GlobalClass;
import com.example.rolegame.Objects.Profile;

import java.util.ArrayList;

public class Preparation extends AppCompatActivity {

    //variables
    Button next_button;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    ProfileDataBaseHelper myDB; //profile data base
    ArrayList<Profile> profiles; //The ArrayList where all profiles are going to be loaded into.
    PreparationAdapter customAdapter; //The adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparation);

        //getting the references.
        recyclerView = findViewById(R.id.recyclerView);
        next_button = findViewById(R.id.next_button);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saves the party players that are going to play the game.
                GlobalClass.setProfiles(profiles);

                Intent intent = new Intent(Preparation.this, RoleManager.class);
                //intent.putExtra("ChosenProfiles",profiles);
                startActivity(intent);
            }
        });

        //----------adapter part------------
        myDB = new ProfileDataBaseHelper(Preparation.this);

        //the profiles that are going to play the game.
        profiles = new ArrayList<Profile>();

        //Setting up the custom adapter
        layoutManager = new GridLayoutManager(this, 3,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        storeDataInArrays();

        customAdapter = new PreparationAdapter(Preparation.this, profiles);
        recyclerView.setAdapter(customAdapter);

    }

    //adds the menu icons to the action bar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    //sets an onClick on the appbar icons that transfers the user the wanted activity.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //sends the user to the profile list activity.
        if (item.getItemId() == R.id.people) {
            Intent intent = new Intent(Preparation.this, ProfileList.class);
            startActivityForResult(intent, 3);
            return true;
        }
        //sends the user to the new profile activity.
        else if (item.getItemId() == R.id.add) {
            Intent intent = new Intent(Preparation.this, NewProfile.class);
            startActivityForResult(intent, 3);
        }
        return false;
    }

    //resets the activity (refreshing the adapter) when the user came back from profile list.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {
                finish();
                startActivity(getIntent());
            }
        }
    }

    //Stores the database data in arrays.
    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "no data.", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                Profile p = new Profile(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3) == 1 ? true: false);
                //checks if the profile is chosen for the current game, if it is, add it to the array list.
                if (p.isChosen())
                {
                    profiles.add(p);
                }
            }
        }
        cursor.close();
        myDB.close();
    }
}