package com.example.rolegame.Game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rolegame.Adapters.GameAdapter;
import com.example.rolegame.Adapters.PreparationAdapter;
import com.example.rolegame.Objects.Ability;
import com.example.rolegame.Objects.GlobalClass;
import com.example.rolegame.Objects.Player;
import com.example.rolegame.Preparation;
import com.example.rolegame.R;

import java.util.ArrayList;

public class NightShow extends AppCompatActivity {

    Button button, two1, two2, three1, three2, three3;
    TextView role_name;
    ImageView role_image;
    ArrayList<Integer> roleImgList = GlobalClass.getImages();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    GameAdapter customAdapter;
    Ability currentAbility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_night_show);

        //getting from the intent the profiles playing.
        Intent getIntent = getIntent();
        final ArrayList<Player> players = (ArrayList<Player>) getIntent.getSerializableExtra("ChosenPlayers");
        int order = getIntent.getIntExtra("order",0);

        Player player = players.get(order);

        //checks if the next player is alive.
        if (order < players.size()-1) {
            while (!players.get(order + 1).isAlive()) {
                order++;
                if ((order + 1) == players.size()) {
                    break;
                }
            }
        }
        
        order++;

        //creating an arrayList for alive players only.
        ArrayList<Player> alivePlayers = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isAlive()) {
                alivePlayers.add(players.get(i));
            }
        }
        //removing the current player from the list
        for (int i = 0; i < alivePlayers.size(); i++) {
            if (alivePlayers.get(i).getTurn() == player.getTurn()) {
                alivePlayers.remove(i);
            }
        }

        //getting the references
        button = findViewById(R.id.button);
        role_image = findViewById(R.id.imageView);
        role_name = findViewById(R.id.name_txt);
        recyclerView = findViewById(R.id.recyclerView);

        //buttons
        two1 = findViewById(R.id.twoButton1);
        two2 = findViewById(R.id.twoButton2);
        three1 = findViewById(R.id.threeButton1);
        three2 = findViewById(R.id.threeButton2);
        three3 = findViewById(R.id.threeButton3);

        role_name.setText(player.getRole().getName());
        role_image.setImageResource(roleImgList.get(player.getRole().getImagePosition()));

        int countActives = 0;
        ArrayList<Ability> activeAbilities = new ArrayList<>();

        if (player.getRole().getAbilities() != null) {
            for (int i = 0; i < player.getRole().getAbilities().length; i++)
            {
                if (player.getRole().getAbilities()[i].isActive())
                {
                    activeAbilities.add(player.getRole().getAbilities()[i]);
                    countActives++;
                }
            }
        }

        switch (countActives) {
            case 0:
                recyclerView.setEnabled(false);
                break;
            case 1:
                currentAbility = activeAbilities.get(0);

                //Setting up the custom adapter
                setCustomAdapter(players, alivePlayers, currentAbility);

                break;
            case 2:
                two1.setVisibility(View.VISIBLE);
                two2.setVisibility(View.VISIBLE);

                two1.setText(activeAbilities.get(0).getName());
                two2.setText(activeAbilities.get(1).getName());

                two1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentAbility = activeAbilities.get(0);
                        //Setting up the custom adapter
                        setCustomAdapter(players, alivePlayers, currentAbility);
                    }
                });

                two2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentAbility = activeAbilities.get(1);
                        //Setting up the custom adapter
                        setCustomAdapter(players, alivePlayers, currentAbility);
                    }
                });

                break;
            case 3:
                three1.setVisibility(View.VISIBLE);
                three2.setVisibility(View.VISIBLE);
                three3.setVisibility(View.VISIBLE);

                three1.setText(activeAbilities.get(0).getName());
                three2.setText(activeAbilities.get(1).getName());
                three3.setText(activeAbilities.get(2).getName());

                three1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentAbility = activeAbilities.get(0);
                        //Setting up the custom adapter
                        setCustomAdapter(players, alivePlayers, currentAbility);
                    }
                });

                three2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentAbility = activeAbilities.get(1);
                        //Setting up the custom adapter
                        setCustomAdapter(players, alivePlayers, currentAbility);
                    }
                });

                three3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentAbility = activeAbilities.get(2);
                        //Setting up the custom adapter
                        setCustomAdapter(players, alivePlayers, currentAbility);
                    }
                });

                break;
        }

        int finalOrder = order;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Effects
                if (customAdapter != null) {
                    for (int i = 0; i < customAdapter.getChosenPlayers().size(); i++)
                    {
                        int currentPlayer = customAdapter.getChosenPlayers().get(i);
                        ArrayList <String> effects = alivePlayers.get(currentPlayer).getActiveEffect();
                        if (!alivePlayers.get(currentPlayer).getActiveEffect().contains(currentAbility.getEffect())) {
                            effects.add(currentAbility.getEffect());
                            alivePlayers.get(currentPlayer).setActiveEffect(effects);
                            alivePlayers.get(currentPlayer).addEffectGiver(player);
                            for (int j = 0; j < player.getRole().getAbilities().length; j++) {
                                if (player.getRole().getAbilities()[j].isSideEffect())
                                {
                                    effects.add(player.getRole().getAbilities()[j].getEffect());
                                    alivePlayers.get(currentPlayer).addEffectGiver(player);
                                }
                            }
                        }
                    }
                }


                Intent send = new Intent(NightShow.this, Night.class);
                send.putExtra("ChosenPlayers",players);
                send.putExtra("order", finalOrder);

                startActivity(send);
                finish();
            }
        });
    }

    void setCustomAdapter(ArrayList<Player> players, ArrayList<Player> alivePlayers, Ability currentAbility) {

        layoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //in case the ability is revive
        if (currentAbility.getId() == 4) {

            //replace alivePlayers with dead players
            ArrayList<Player> deadPlayers = new ArrayList<>();
            for (int i = 0; i < players.size(); i++) {
                if (!players.get(i).isAlive()) {
                    deadPlayers.add(players.get(i));
                }
            }
            customAdapter = new GameAdapter(NightShow.this, players,  deadPlayers, button);
        }
        else {
            customAdapter = new GameAdapter(NightShow.this, players,  alivePlayers, button);
        }
        recyclerView.setAdapter(customAdapter);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(NightShow.this);
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