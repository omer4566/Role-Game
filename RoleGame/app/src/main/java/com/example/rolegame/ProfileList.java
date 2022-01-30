package com.example.rolegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rolegame.Adapters.ProfileListAdapter;
import com.example.rolegame.DataBase.ProfileDataBaseHelper;
import com.example.rolegame.Objects.Profile;

import java.util.ArrayList;

public class ProfileList extends AppCompatActivity {

    //variables
    Button save_button;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    ProfileDataBaseHelper myDB;
    ArrayList<Profile> profiles;
    ProfileListAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        //getting the references.
        recyclerView = findViewById(R.id.recyclerView);
        save_button = findViewById(R.id.save_button);

        //on clicking the save button
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateData();

                //setting the result and sending the user back to the preparation activity.
                Intent intent = new Intent(ProfileList.this, Preparation.class);
                setResult(Activity.RESULT_OK,intent);

                //Finishes the activity and plays the closing animation.
                finish();
            }
        });

        myDB = new ProfileDataBaseHelper(ProfileList.this);
        profiles = new ArrayList<Profile>();

        //Setting up the custom adapter
        recyclerView.setLayoutManager(layoutManager);

        storeDataInArrays();

        customAdapter = new ProfileListAdapter(ProfileList.this, profiles);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProfileList.this));

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
                profiles.add(p);
            }
        }
        cursor.close();
        myDB.close();
    }

    //Updating the IsChosen row in the database.
    void updateData() {

        for (int i = 0; i < profiles.size();i++) {

            myDB.updateIsChosen(String.valueOf(profiles.get(i).getId()), profiles.get(i).isBool());
        }
        myDB.close();
    }
}