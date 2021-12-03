package com.example.websave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class SplashScreenActivity extends AppCompatActivity {
    private ProgressBar mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                new Thread(new Runnable() {
                    public void run() {

                    }
                }).start();
            }
        }, 1000);





        setContentView(R.layout.activity_splash_screen);
        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
                finish();
            }
        }).start();

    }
    private void doWork() {
            try {
                Thread.sleep(1000);

            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    private void startApp() {
        Intent intent = new Intent(SplashScreenActivity.this, OnBoardingScreen.class);
        startActivity(intent);
    }
}