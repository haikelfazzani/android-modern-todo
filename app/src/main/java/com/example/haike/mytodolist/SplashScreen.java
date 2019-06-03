package com.example.haike.mytodolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.haike.mytodolist.activity.TodoForm;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 10000;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.progressBar);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    doJob();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    private void doJob() throws InterruptedException {
        for (int i = 10 ; i <= 50 ; i+= 10) {

            Thread.sleep(1000);
            progressBar.setProgress(i);
            if(i >= 50) {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
