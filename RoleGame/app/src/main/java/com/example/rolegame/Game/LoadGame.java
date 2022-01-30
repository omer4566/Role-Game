package com.example.rolegame.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.rolegame.Objects.Ability;
import com.example.rolegame.Objects.GameClass;
import com.example.rolegame.Objects.Mechanics;
import com.example.rolegame.Objects.Player;
import com.example.rolegame.Objects.Profile;
import com.example.rolegame.Objects.Role;
import com.example.rolegame.R;

import java.util.ArrayList;
import java.util.Collections;

public class LoadGame extends AppCompatActivity {

    ArrayList<Player> players;
    int order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_load_activity);

        //getting from the intent the profiles playing.
        Intent getIntent = getIntent();
        ArrayList<Profile> profiles = (ArrayList<Profile>) getIntent.getSerializableExtra("ChosenProfiles");
        ArrayList<Role> roles = (ArrayList<Role>) getIntent.getSerializableExtra("ChosenRoles");

        GameClass.setCurrentIGEffects(roles);

        //shuffles the profiles
        Collections.shuffle(profiles);
        ArrayList<Role> chosenRoles = new ArrayList<>();

        for (int i = 0; i < roles.size(); i++) {
            ArrayList<Mechanics> roleMechanics = roles.get(i).getMechanics();
            String type1 = null;
            String type2 = null;

            if (roleMechanics != null) {
                for (int j = 0; j < roleMechanics.size(); j++) {
                    int type = roleMechanics.get(j).getType();
                    if (type == 1) {
                        type1 = roleMechanics.get(j).getName();
                    }
                    else if (type == 2) {
                        type2 = roleMechanics.get(j).getName();
                    }
                }
            }
            if (type1 != null) {
                switch (type1) {
                    case "At least 1":
                        chosenRoles.add(roles.get(i));
                        break;
                    case "At least 2":
                        chosenRoles.add(roles.get(i));
                        chosenRoles.add(roles.get(i));
                        break;
                }
            }
            if (type2 != null) {
                int count = 0;
                for (int j = 0; j < chosenRoles.size(); j++) {
                    if (chosenRoles.get(j) == roles.get(i)) {
                        count++;
                    }
                }
                switch (type2) {
                    case "1 per 3":
                        while (profiles.size() / 3 > count) {
                            count++;
                            chosenRoles.add(roles.get(i));
                        }
                        break;
                    case "1 per 4":
                        while (profiles.size() / 4 > count) {
                            count++;
                            chosenRoles.add(roles.get(i));
                        }
                        break;
                    case "1 per 5":
                        while (profiles.size() / 5 > count) {
                            count++;
                            chosenRoles.add(roles.get(i));
                        }
                        break;
                    case "1 per 6":
                        while (profiles.size() / 6 > count) {
                            count++;
                            chosenRoles.add(roles.get(i));
                        }
                        break;
                }
            }
        }

        while (chosenRoles.size() < profiles.size()) {

            Collections.shuffle(roles);
            ArrayList<Mechanics> roleMechanics = roles.get(0).getMechanics();

            if (roleMechanics != null) {
                for (int i = 0; i < roleMechanics.size(); i++) {
                    if (roleMechanics.get(i).getType() == 3) {
                        String type = roleMechanics.get(i).getName();

                        int count = 0;
                        for (int j = 0; j < chosenRoles.size(); j++) {
                            if (chosenRoles.get(j) == roles.get(0)) {
                                count++;
                            }
                        }
                        switch (type) {
                            case "Max 1":
                                while (count > 1)
                                {
                                    for (int j = 0; j < chosenRoles.size(); j++) {
                                        if (chosenRoles.get(j) == roles.get(0)) {
                                            chosenRoles.remove(j);
                                            count--;
                                        }
                                    }
                                }

                                if (count < 1) {
                                    chosenRoles.add(roles.get(0));
                                }
                                break;
                            case "Max 2":
                                while (count > 2)
                                {
                                    for (int j = 0; j < chosenRoles.size(); j++) {
                                        if (chosenRoles.get(j) == roles.get(0)) {
                                            chosenRoles.remove(j);
                                        }
                                    }
                                }

                                if (count < 1) {
                                    chosenRoles.add(roles.get(0));
                                }
                                break;
                        }
                    }
                    else
                    {
                        chosenRoles.add(roles.get(0));
                    }
                }
            }
            else {
                chosenRoles.add(roles.get(0));
            }
        }

        ArrayList <String> activeEffects = new ArrayList<>();
        players = new ArrayList<Player>();
        //get random role, for now, its 1 role for each one.
        Collections.shuffle(chosenRoles);
        for (int i = 0; i < profiles.size(); i++)
        {
            ArrayList <Ability> passiveAbilities = new ArrayList<>();

            if (chosenRoles.get(i).getAbilities() != null) {
                for (int j = 0; j < chosenRoles.get(i).getAbilities().length; j++) {
                    if (!chosenRoles.get(i).getAbilities()[j].isSideEffect() && !chosenRoles.get(i).getAbilities()[j].isActive()) {
                        passiveAbilities.add(chosenRoles.get(i).getAbilities()[j]);
                    }
                }
            }
            Profile profile = profiles.get(i);

            Player player = new Player(chosenRoles.get(i), profile, i, true, activeEffects,passiveAbilities);
            players.add(player);
        }

        order = 0;

        Intent send = new Intent(LoadGame.this, Night.class);
        send.putExtra("ChosenPlayers",players);
        send.putExtra("order", order);
        startActivity(send);
        finish();
    }
}