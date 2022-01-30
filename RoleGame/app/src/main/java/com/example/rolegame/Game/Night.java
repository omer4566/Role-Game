package com.example.rolegame.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rolegame.Objects.Player;
import com.example.rolegame.Objects.Profile;
import com.example.rolegame.R;
import com.example.rolegame.RoleManager;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Night extends AppCompatActivity {

    CircleImageView civ;
    Button button;
    TextView name_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_night);

        //getting from the intent the profiles playing.
        Intent getIntent = getIntent();
        ArrayList<Player> players = (ArrayList<Player>) getIntent.getSerializableExtra("ChosenPlayers");
        int order = getIntent.getIntExtra("order", 0);

        //checking if all players got their role
        if (order == players.size())
        {
            order = 0;
            //night over, start day cycle
            Intent dayCycle = new Intent(Night.this, Morning.class);
            dayCycle.putExtra("ChosenPlayers",players);

            startActivity(dayCycle);
            finish();
        }

        //using the order to get the right player
        Player player = players.get(order);

        //getting the references
        civ = findViewById(R.id.CIV);
        button = findViewById(R.id.button);
        name_txt = findViewById(R.id.name_txt);

        if (player.isAlive()) {
            if (player.getProfile().getProfileUri() == null)
            {
                civ.setImageResource(R.drawable.nophoto);
            }
            else
            {
                civ.setImageURI(Uri.parse(player.getProfile().getProfileUri()));
            }
            name_txt.setText(player.getProfile().getName());
        }
        else if (order < players.size()) {
            while (!players.get(order).isAlive()) {
                order++;
            }
        }


        int finalOrder = order;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(Night.this, NightShow.class);
                send.putExtra("ChosenPlayers",players);
                send.putExtra("order", finalOrder);

                startActivity(send);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Night.this);
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