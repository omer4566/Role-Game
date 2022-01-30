package com.example.rolegame.Game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rolegame.Adapters.GameAdapter;
import com.example.rolegame.Adapters.MorningAdapter;
import com.example.rolegame.Objects.GameClass;
import com.example.rolegame.Objects.Player;
import com.example.rolegame.Objects.Role;
import com.example.rolegame.ProfileList;
import com.example.rolegame.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

public class Morning extends AppCompatActivity {

    TextView text;
    Button button;
    ArrayList<String> activeEffects;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MorningAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_morning);

        //getting from the intent the profiles playing.
        Intent getIntent = getIntent();
        ArrayList<Player> players = (ArrayList<Player>) getIntent.getSerializableExtra("ChosenPlayers");

        button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recyclerView);
        text = findViewById(R.id.text);

        ArrayList<String> txt = new ArrayList<>();
        ArrayList<Player> players2send = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            activeEffects = players.get(i).getActiveEffect();

            if (activeEffects != null) {
                if (activeEffects.size() == 1) {
                    switch (activeEffects.get(0)) {
                        //No effects that depends on other effects.
                        case "Death":
                            boolean finish = false;
                            for (int j = 0; j < players.get(i).getPassiveAbilities().size(); j++) {
                                if (players.get(i).getPassiveAbilities().get(j).getEffect().equals("Protected")) {
                                    switch (players.get(i).getPassiveAbilities().get(j).getName()) {
                                        case "invulnerable":
                                            finish = true;
                                            break;
                                        case "shield":
                                            Random random = new Random();
                                            int rand_int1 = random.nextInt(2);
                                            if (rand_int1 == 1) {
                                                finish = true;
                                            }
                                            break;
                                    }
                                }
                            }
                            if (!finish) {
                                players.get(i).setAlive(false);
                                txt.add("Has died.");
                                players2send.add(players.get(i));
                            }
                            break;
                        case "Reborn":
                            players.get(i).setAlive(true);
                            txt.add("Was revived.");
                            players2send.add(players.get(i));
                            break;
                        case "Flip":
                            if (players.get(i).getRole().getTeam() == 1)
                            {
                                players.get(i).getRole().setTeam(3);
                            }
                            else if (players.get(i).getRole().getTeam() == 3)
                            {
                                players.get(i).getRole().setTeam(1);
                            }
                            break;
                        case "Replace":
                            final Role role = players.get(i).getRole();
                            for (int r = 0; r < activeEffects.size(); r++)
                            {
                                if (activeEffects.get(r).contains("Replace"))
                                {
                                    players.get(i).setRole(players.get(i).getEffectGiver().get(r).getRole());
                                    for (int l = 0; l < players.size(); l++) {
                                        if (players.get(l).getProfile().getName().equals(players.get(i).getEffectGiver().get(r).getProfile().getName())) {
                                            players.get(l).setRole(role);
                                        }
                                    }
                                }
                            }
                    }
                }
                else if (activeEffects.size() > 1)
                {
                    ArrayList<Integer> method = GameClass.checkForNodes(activeEffects);
                    for (int j = 0; j < method.size(); j++){
                        int num = method.get(j);

                        switch (num) {
                            case 1:
                                //protected effect activated.
                                txt.add("Was protected.");
                                players2send.add(players.get(i));
                                break;
                            case 2:
                                //zombiefaction effect activated.
                                for (int r = 0; r < activeEffects.size(); r++)
                                {
                                    if (activeEffects.get(r).contains("Zombiefaction"))
                                    {
                                        players.get(i).setRole(players.get(i).getEffectGiver().get(r).getRole());
                                    }
                                }
                                break;
                            case 3:
                                //death effect activated.
                                boolean finish = false;
                                for (int l = 0; l < players.get(i).getPassiveAbilities().size(); l++) {
                                    if (players.get(i).getPassiveAbilities().get(l).getEffect().equals("Protected")) {
                                        switch (players.get(i).getPassiveAbilities().get(l).getName()) {
                                            case "invulnerable":
                                                finish = true;
                                                break;
                                            case "shield":
                                                Random random = new Random();
                                                int rand_int1 = random.nextInt(2);
                                                if (rand_int1 == 1) {
                                                    finish = true;
                                                }
                                                break;
                                        }
                                    }
                                }
                                if (!finish) {
                                    players.get(i).setAlive(false);
                                    txt.add("Has died.");
                                    players2send.add(players.get(i));
                                }
                                break;
                            case 4:
                                //reborn effect with zombie
                                for (int r = 0; r < activeEffects.size(); r++)
                                {
                                    if (activeEffects.get(r).contains("Zombiefaction"))
                                    {
                                        players.get(i).setRole(players.get(i).getEffectGiver().get(r).getRole());
                                    }
                                }

                                players.get(i).setAlive(true);
                                txt.add("Was revived.");
                                players2send.add(players.get(i));
                                break;
                            case 5:
                                //reborn effect activated
                                players.get(i).setAlive(true);
                                txt.add("Was revived.");
                                players2send.add(players.get(i));
                                break;
                            case 7:
                                final Role role = players.get(i).getRole();
                                for (int r = 0; r < activeEffects.size(); r++)
                                {
                                    if (activeEffects.get(r).contains("Replace"))
                                    {
                                        players.get(i).setRole(players.get(i).getEffectGiver().get(r).getRole());
                                        for (int l = 0; l < players.size(); l++) {
                                            if (players.get(l).getProfile().getName().equals(players.get(i).getEffectGiver().get(r).getProfile().getName())) {
                                                players.get(l).setRole(role);
                                            }
                                        }
                                    }
                                }
                                break;
                            case 9:
                                if (players.get(i).getRole().getTeam() == 1)
                                {
                                    players.get(i).getRole().setTeam(3);
                                }
                                else if (players.get(i).getRole().getTeam() == 3)
                                {
                                    players.get(i).getRole().setTeam(1);
                                }
                                break;
                        }
                    }
                }
                players.get(i).clearEffects();
                //here set pregame role effects.
            }
        }

        layoutManager = new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        if (players2send.size() == 0) {
            text.setText("Nothing out of the ordinary happened last night.");
        }

        customAdapter = new MorningAdapter(Morning.this, txt, players2send);
        recyclerView.setAdapter(customAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int dead = 0;
                int good = 0, bad = 0;

                for (int i = 0; i < players.size(); i++) {
                    if (!players.get(i).isAlive()) {
                        dead++;
                    }
                    else {
                        if (players.get(i).getRole().getTeam() == 1) {
                            good++;
                        }
                        else if (players.get(i).getRole().getTeam() == 3) {
                            bad++;
                        }
                    }
                }
                if (dead == players.size()){
                    //everyone died.
                    Intent intent = new Intent(Morning.this, WinningLayout.class);
                    intent.putExtra("ChosenPlayers",players);
                    intent.putExtra("result", 0);
                    startActivity(intent);
                    finish();
                }
                else if (dead == players.size() - 1) {
                    //there is only one player alive.
                    Intent intent = new Intent(Morning.this, WinningLayout.class);
                    intent.putExtra("ChosenPlayers",players);
                    intent.putExtra("result", 1);
                    startActivity(intent);
                    finish();
                }
                else if (dead + good == players.size()) {
                    //only good people left.
                    Intent intent = new Intent(Morning.this, WinningLayout.class);
                    intent.putExtra("ChosenPlayers",players);
                    intent.putExtra("result", 2);
                    startActivity(intent);
                    finish();
                }
                else if (dead + bad == players.size()) {
                    //only bad people left.
                    Intent intent = new Intent(Morning.this, WinningLayout.class);
                    intent.putExtra("ChosenPlayers",players);
                    intent.putExtra("result", 3);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(Morning.this, Discussion.class);
                    intent.putExtra("ChosenPlayers",players);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Morning.this);
        alert.setTitle("Exit");
        alert.setMessage("Are you sure you want to leave the current game?");
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.create().show();
    }
}