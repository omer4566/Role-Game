package com.example.rolegame.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.rolegame.Objects.Player;
import com.example.rolegame.Preparation;
import com.example.rolegame.R;

import java.util.ArrayList;

public class Discussion extends AppCompatActivity {

    Button button;
    int order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_discussion);

        //getting from the intent the profiles playing.
        Intent getIntent = getIntent();
        ArrayList<Player> players = (ArrayList<Player>) getIntent.getSerializableExtra("ChosenPlayers");

        button = findViewById(R.id.button);

        order = 0;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                while (!players.get(order).isAlive())
                {
                    order++;
                    if (order > players.size()) {
                        Intent intent = new Intent(Discussion.this, Preparation.class);
                    }
                }

                Intent intent = new Intent(Discussion.this, VoteTime.class);
                intent.putExtra("ChosenPlayers",players);
                intent.putExtra("order", order);
                startActivity(intent);
                finish();
            }
        });
    }

    //this function stops the player and asks him if he is sure about leaving the game.
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Discussion.this);
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