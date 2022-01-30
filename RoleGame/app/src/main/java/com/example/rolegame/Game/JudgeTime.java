package com.example.rolegame.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rolegame.Objects.Player;
import com.example.rolegame.Preparation;
import com.example.rolegame.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class JudgeTime extends AppCompatActivity {

    int order;
    CircleImageView civ;
    TextView name_txt, text;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_judge_time);

        //getting from the intent the profiles playing.
        Intent getIntent = getIntent();
        ArrayList<Player> players = (ArrayList<Player>) getIntent.getSerializableExtra("ChosenPlayers");
        int integer = getIntent.getIntExtra("integer", 0);

        //getting the references
        civ = findViewById(R.id.CIV);
        name_txt = findViewById(R.id.name_txt);
        text = findViewById(R.id.text);
        button = findViewById(R.id.button);

        //resets the votes.
        for (int i = 0; i < players.size(); i++) {
            players.get(i).resetVotes();
        }

        if (integer == 1) {
            //Tie.
            text.setText("There was a tie in the votes.");
        }
        else if (integer == 2) {
            //someone got killed.
            Player deadPlayer = (Player) getIntent.getSerializableExtra("DeadPlayer");

            boolean finish = false;

            //check if the player is trustworthy
            for (int i = 0; i < deadPlayer.getPassiveAbilities().size(); i++) {
                if (deadPlayer.getPassiveAbilities().get(i).getId() == 6) {

                    if (deadPlayer.getProfile().getProfileUri() == null)
                    {
                        civ.setImageResource(R.drawable.nophoto);
                    }
                    else
                    {
                        civ.setImageURI(Uri.parse(deadPlayer.getProfile().getProfileUri()));
                    }
                    name_txt.setText(deadPlayer.getProfile().getName());
                    text.setText(deadPlayer.getProfile().getName() + " will remain alive as he seems trustworthy by the members of the village");
                    finish = true;
                }
            }

            if (!finish) {
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).getProfile().getName().equals(deadPlayer.getProfile().getName())) {
                        players.get(i).setAlive(false);
                    }
                }

                if (deadPlayer.getProfile().getProfileUri() == null)
                {
                    civ.setImageResource(R.drawable.nophoto);
                }
                else
                {
                    civ.setImageURI(Uri.parse(deadPlayer.getProfile().getProfileUri()));
                }
                name_txt.setText(deadPlayer.getProfile().getName());
                text.setText(deadPlayer.getProfile().getName() + "Has being selected as guilty by the other players.");
            }
        }
        else if (integer == 3) {
            //no one voted.
            text.setText("No one, not a single soul on earth, has voted.");
        }

        order = 0;

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
                    Intent intent = new Intent(JudgeTime.this, WinningLayout.class);
                    intent.putExtra("ChosenPlayers",players);
                    intent.putExtra("result", 0);
                    startActivity(intent);
                    finish();
                }
                else if (dead == players.size() - 1) {
                    //there is only one player alive.
                    Intent intent = new Intent(JudgeTime.this, WinningLayout.class);
                    intent.putExtra("ChosenPlayers",players);
                    intent.putExtra("result", 1);
                    startActivity(intent);
                    finish();
                }
                else if (dead + good == players.size()) {
                    //only good people left.
                    Intent intent = new Intent(JudgeTime.this, WinningLayout.class);
                    intent.putExtra("ChosenPlayers",players);
                    intent.putExtra("result", 2);
                    startActivity(intent);
                    finish();
                }
                else if (dead + bad == players.size()) {
                    //only bad people left.
                    Intent intent = new Intent(JudgeTime.this, WinningLayout.class);
                    intent.putExtra("ChosenPlayers",players);
                    intent.putExtra("result", 3);
                    startActivity(intent);
                    finish();
                }
                else {
                    while (!players.get(order).isAlive())
                    {
                        order++;
                        if (order > players.size()) {
                            Intent intent = new Intent(JudgeTime.this, Preparation.class);
                        }
                    }

                    //judgement time over, start night cycle
                    Intent dayCycle = new Intent(JudgeTime.this, Night.class);
                    dayCycle.putExtra("ChosenPlayers",players);
                    dayCycle.putExtra("order", order);

                    startActivity(dayCycle);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(JudgeTime.this);
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