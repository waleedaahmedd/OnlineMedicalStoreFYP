package com.example.onlinemedicalstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView splashLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashLogo = findViewById(R.id.splash_logo);

        // To Run Animation
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        splashLogo.startAnimation(animation);



        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 2 seconds
                    sleep(2 * 1500);


                    // Forward to next screen
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();

                } catch (Exception e) {
                    Log.d("Splash", "run: " + e.getMessage());
                }
            }
        };

        background.start();


    }
}