package com.example.rolegame.Game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rolegame.Adapters.GameAdapter;
import com.example.rolegame.Objects.Player;
import com.example.rolegame.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class VoteTime extends AppCompatActivity {

    CircleImageView civ;
    Button button;
    TextView name_txt;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    GameAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_vote_time);

        //getting from the intent the profiles playing.
        Intent getIntent = getIntent();
        ArrayList<Player> players = (ArrayList<Player>) getIntent.getSerializableExtra("ChosenPlayers");
        int order = getIntent.getIntExtra("order", 0);

        //checking if all players got to vote.
        if (order == players.size())
        {
            order = 0;

            //judgement time!
            int mostVotes = 0;
            int index = (players.size() + 1);

            ArrayList <Integer> arrayList = new ArrayList<>();

            for (int i = 0; i < players.size(); i++) {
                arrayList.add(players.get(i).getVotes());
            }

            for (int i = 0; i < arrayList.size(); i++) {
                if (mostVotes < arrayList.get(i)) {
                    mostVotes = arrayList.get(i);
                    index = i;
                }
            }

            if (index != (players.size() + 1)) {
                arrayList.remove(index);
                if (arrayList.contains(mostVotes)) {
                    //there is a tie in the votes.
                    Intent next = new Intent(VoteTime.this, JudgeTime.class);
                    next.putExtra("ChosenPlayers",players);
                    next.putExtra("integer", 1 );

                    startActivity(next);
                    finish();
                }
                else {
                    //someone has to die.
                    for (int i = 0; i < players.size(); i++) {
                        if (players.get(i).getVotes() == mostVotes) {
                            Intent next = new Intent(VoteTime.this, JudgeTime.class);
                            next.putExtra("ChosenPlayers",players);
                            next.putExtra("DeadPlayer",players.get(i));
                            next.putExtra("integer", 2 );

                            startActivity(next);
                            finish();
                        }
                    }
                }
            }
            else
            {
                //voting time over, its judgement time.
                Intent next = new Intent(VoteTime.this, JudgeTime.class);
                next.putExtra("ChosenPlayers",players);
                next.putExtra("integer", 3 );

                startActivity(next);
                finish();
            }
        }

        //using the order to get the right player
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

        //getting the references
        civ = findViewById(R.id.CIV);
        button = findViewById(R.id.button);
        name_txt = findViewById(R.id.name_txt);
        recyclerView = findViewById(R.id.recyclerView);

        if (player.getProfile().getProfileUri() == null)
        {
            civ.setImageResource(R.drawable.nophoto);
        }
        else
        {
            civ.setImageURI(Uri.parse(player.getProfile().getProfileUri()));
        }
        name_txt.setText(player.getProfile().getName());

        layoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //creating an arrayList for alive players only.
        ArrayList<Player> alivePlayers = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isAlive()) {
                alivePlayers.add(players.get(i));
            }
        }

        customAdapter = new GameAdapter(VoteTime.this, players, alivePlayers, button);
        recyclerView.setAdapter(customAdapter);

        int finalOrder = order;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Voting
                for (int i = 0; i < customAdapter.getChosenPlayers().size(); i++) {
                    int currentPlayer = customAdapter.getChosenPlayers().get(i);
                    alivePlayers.get(currentPlayer).addVotes(1);
                }

                //returning the alive players to the main player array list

                //refresh the current activity and change the contents.
                finish();
                getIntent.putExtra("ChosenPlayers",players);
                getIntent.putExtra("order", finalOrder);
                startActivity(getIntent());
            }
        });
    }

    //this function stops the player and asks him if he is sure about leaving the game.
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(VoteTime.this);
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