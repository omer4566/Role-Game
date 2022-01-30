package com.example.rolegame.Objects;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolegame.Adapters.RoleManagerAdapter;
import com.example.rolegame.Adapters.RoleManagerAdapter2;
import com.example.rolegame.DataBase.RoleDataBaseHelper;
import com.example.rolegame.Game.LoadGame;
import com.example.rolegame.NewRole;
import com.example.rolegame.R;

import java.util.ArrayList;

public class test {
}

/*
package com.example.rolegame;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.SwitchCompat;
        import androidx.recyclerview.widget.GridLayoutManager;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CompoundButton;
        import android.widget.Toast;

        import com.example.rolegame.Adapters.RoleManagerAdapter;
        import com.example.rolegame.Adapters.RoleManagerAdapter2;
        import com.example.rolegame.DataBase.RoleDataBaseHelper;
        import com.example.rolegame.Game.LoadGame;
        import com.example.rolegame.Objects.Ability;
        import com.example.rolegame.Objects.GlobalClass;
        import com.example.rolegame.Objects.Mechanics;
        import com.example.rolegame.Objects.Profile;
        import com.example.rolegame.Objects.Role;

        import java.util.ArrayList;

public class RoleManager extends AppCompatActivity {

    //variables
    SwitchCompat randomRoles;
    Button next_button;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Role> roles;
    RoleDataBaseHelper myDB;
    RoleManagerAdapter2 customAdapter2;
    RoleManagerAdapter customAdapter;
    boolean adapter2Active;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_manager);

        //getting from the intent the profiles playing.
        //Intent getIntent = getIntent();
        //ArrayList<Profile> profiles = (ArrayList<Profile>) getIntent.getSerializableExtra("ChosenProfiles");
        ArrayList<Profile> profiles = GlobalClass.getProfiles();

        //getting the references
        recyclerView = findViewById(R.id.recyclerView);
        //randomRoles = findViewById(R.id.switchCompat);
        next_button = findViewById(R.id.next_button);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter2Active)
                {
                    if (customAdapter2.getTotalNum() < profiles.size())
                    {
                        AlertDialog.Builder alert = new AlertDialog.Builder(com.example.rolegame.RoleManager.this);
                        alert.setTitle("Issue Found");
                        alert.setMessage("Not all players are adjusted to a role.");
                        alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alert.create().show();
                    }
                    else
                    {
                        //next activity.
                        //saves the party players that are going to play the game.
                        Intent send = new Intent(com.example.rolegame.RoleManager.this, LoadGame.class);
                        send.putExtra("ChosenProfiles",profiles);
                        send.putExtra("ChosenRoles",roles);
                        startActivity(send);
                        finish();
                    }
                }
                else {
                    int num = 0;
                    roles = customAdapter.getUpdated();
                    ArrayList<Role> chosenRoles = new ArrayList<Role>();
                    for (int i = 0; i < roles.size(); i++)
                    {
                        if (roles.get(i).isChosen()) {
                            chosenRoles.add(num, roles.get(i));
                            num++;
                        }
                    }
                    if (num > 1) {
                        //next activity.
                        //saves the party players that are going to play the game.
                        Intent send = new Intent(com.example.rolegame.RoleManager.this, LoadGame.class);
                        send.putExtra("ChosenProfiles",profiles);
                        send.putExtra("ChosenRoles",chosenRoles);
                        startActivity(send);
                        finish();
                    }
                    else
                    {
                        AlertDialog.Builder alert = new AlertDialog.Builder(com.example.rolegame.RoleManager.this);
                        alert.setTitle("Issue Found");
                        alert.setMessage("At least 2 roles must be active to start a game.");
                        alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alert.create().show();
                    }
                }
            }
        });

        /*
        randomRoles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    customAdapter = new RoleManagerAdapter(RoleManager.this, roles);
                    recyclerView.setAdapter(customAdapter);
                    adapter2Active = false;
                }
                else
                {
                    customAdapter2 = new RoleManagerAdapter2(RoleManager.this, roles,profiles.size());
                    recyclerView.setAdapter(customAdapter2);
                    adapter2Active = true;
                }
            }
        });
*/
/*
        //adapter part
        myDB = new RoleDataBaseHelper(com.example.rolegame.RoleManager.this);

        //the roles that are supposed to be shown
        roles = new ArrayList<Role>();

        //Setting up the custom adapter
        layoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        storeDataInArrays();


        customAdapter = new RoleManagerAdapter(com.example.rolegame.RoleManager.this, roles);
        recyclerView.setAdapter(customAdapter);
        adapter2Active = false;

        /*
        customAdapter2 = new RoleManagerAdapter2(RoleManager.this, roles,profiles.size());
        recyclerView.setAdapter(customAdapter2);
        adapter2Active = true;
*//*
        myDB.close();
    }

    //adds the menu icons to the action bar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_role,menu);
        return true;
    }

    //sets an onClick on the appbar icons that transfers the user the wanted activity.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //sends the user to the new profile activity.
        if (item.getItemId() == R.id.add) {
            Intent intent = new Intent(com.example.rolegame.RoleManager.this, NewRole.class);
            //Intent getIntent = getIntent();
            //intent.putExtra("ChosenProfiles",(ArrayList<Profile>) getIntent.getSerializableExtra("ChosenProfiles"));
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
            final Ability[] ability = GlobalClass.getAbility();
            final Mechanics[] mechanic = GlobalClass.getMechanics();
            while (cursor.moveToNext()) {
                //getting image position
                ArrayList<Integer> positions = new ArrayList<>();
                String string = cursor.getString(3);
                positions = GlobalClass.convertStringToArray(string);
                Ability [] abilities;
                if (positions == null)
                {
                    abilities = null;
                }
                else
                {
                    abilities = new Ability[positions.size()];
                    for (int i = 0; i < positions.size(); i++)
                    {
                        abilities [i] = ability [positions.get(i)];
                    }
                }
                //getting mechanics position
                string = cursor.getString(4);
                positions = GlobalClass.convertStringToArray(string);
                ArrayList<Mechanics> mechanics;
                if (positions == null)
                {
                    mechanics = null;
                }
                else
                {
                    mechanics = new ArrayList<>();
                    for (int i = 0; i < positions.size(); i++)
                    {
                        mechanics.add(mechanic[positions.get(i)]);
                    }
                }

                Role r = new Role(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), abilities, mechanics ,cursor.getInt(5), cursor.getString(6), cursor.getInt(7) == 1 ? true: false);
                roles.add(r);
            }
        }
        cursor.close();
        myDB.close();
    }
}*/