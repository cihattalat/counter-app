package com.example.counter_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                }
                catch (Exception e){
                    Log.d("Error", e.toString());
                }
                finally {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }
        };
        thread.start();
    }
}



