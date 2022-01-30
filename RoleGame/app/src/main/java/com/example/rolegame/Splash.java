package com.example.rolegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useless_splash);

        Thread mSplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        //3 Seconds
                        wait(3000);
                    }
                }
                catch (InterruptedException ex) { }

                //finishing the current activity and sending the user to the main menu.
                finish();
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
            }
        };
        mSplashThread.start();
    }
}