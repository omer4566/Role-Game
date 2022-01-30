package com.example.rolegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PreMain extends AppCompatActivity {

    Button guest_button, login_button, register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useless_pre_main);

        //getting the references
        guest_button = findViewById(R.id.guest_button);
        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);

        //on clicking the guest button
        guest_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreMain.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}