package com.example.rolegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button play_button, settings_button, guide_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the references
        play_button = findViewById(R.id.play_button);
        //settings_button = findViewById(R.id.settings_button);
        guide_button = findViewById(R.id.guide_button);

        //on clicking the play button
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Preparation.class);
                startActivity(intent);
            }
        });

        guide_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Guide.class);
                startActivity(intent);
            }
        });

    }
}