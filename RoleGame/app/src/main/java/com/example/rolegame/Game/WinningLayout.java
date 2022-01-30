package com.example.rolegame.Game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rolegame.Adapters.GameAdapter;
import com.example.rolegame.Adapters.MorningAdapter;
import com.example.rolegame.Adapters.PreparationAdapter;
import com.example.rolegame.Adapters.WinningAdapter;
import com.example.rolegame.Objects.Player;
import com.example.rolegame.Preparation;
import com.example.rolegame.R;

import java.util.ArrayList;

public class WinningLayout extends AppCompatActivity {

    Button button;
    TextView topTV, bottomTV;
    RecyclerView topRV, bottomRV;
    RecyclerView.LayoutManager topLM, bottomLM;

    WinningAdapter topCustomAdapter, bottomCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_winning_layout);

        //getting the references.
        button = findViewById(R.id.button);
        topRV = findViewById(R.id.topRecyclerView);
        bottomRV = findViewById(R.id.bottomRecyclerView);
        topTV = findViewById(R.id.topText);
        bottomTV = findViewById(R.id.bottomText);

        //getting from the intent the profiles playing.
        Intent getIntent = getIntent();
        ArrayList<Player> players = (ArrayList<Player>) getIntent.getSerializableExtra("ChosenPlayers");
        int result = getIntent.getIntExtra("result", 0);


        topLM = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
        bottomLM = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);

        topRV.setLayoutManager(topLM);
        bottomRV.setLayoutManager(bottomLM);

        ArrayList<Player> alivePlayers = new ArrayList<>();
        ArrayList<Player> deadPlayers = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isAlive()) {
                alivePlayers.add(players.get(i));
            }
            else
            {
                deadPlayers.add(players.get(i));
            }
        }

        ArrayList<Player> peacefulPlayers = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getRole().getTeam() == 1) {
                peacefulPlayers.add(players.get(i));
            }
        }

        ArrayList<Player> evilPlayers = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getRole().getTeam() == 3) {
                evilPlayers.add(players.get(i));
            }
        }

        if (result == 0) {
            //all died
            topTV.setText("Everybody died, there are no winners.");
            bottomTV.setText("You all lost, shame!");

            bottomCustomAdapter = new WinningAdapter(WinningLayout.this, players);
            bottomRV.setAdapter(bottomCustomAdapter);

        }
        else if (result == 1) {
            topTV.setText("The only surviving winner is:");
            bottomTV.setText("Losers!");

            topCustomAdapter = new WinningAdapter(WinningLayout.this, alivePlayers);
            topRV.setAdapter(topCustomAdapter);

            bottomCustomAdapter = new WinningAdapter(WinningLayout.this, deadPlayers);
            bottomRV.setAdapter(bottomCustomAdapter);
        }
        else if (result == 2) {
            //good guys alive
            topTV.setText("Good people won!");
            bottomTV.setText("Losers!");

            topCustomAdapter = new WinningAdapter(WinningLayout.this, peacefulPlayers);
            topRV.setAdapter(topCustomAdapter);

            bottomCustomAdapter = new WinningAdapter(WinningLayout.this, evilPlayers);
            bottomRV.setAdapter(bottomCustomAdapter);
        }
        else if (result == 3) {
            //bad guys alive
            topTV.setText("Bad people won :(");
            bottomTV.setText("Losers!");

            topCustomAdapter = new WinningAdapter(WinningLayout.this, evilPlayers);
            topRV.setAdapter(topCustomAdapter);

            bottomCustomAdapter = new WinningAdapter(WinningLayout.this, peacefulPlayers);
            bottomRV.setAdapter(bottomCustomAdapter);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WinningLayout.this, Preparation.class);
                startActivity(intent);
            }
        });
    }
}