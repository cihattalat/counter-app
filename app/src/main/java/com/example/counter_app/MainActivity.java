package com.example.counter_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText counterValue;
    Button buttonMinus;
    Button buttonPlus;
    Button buttonSettings;

    SharedPreferencesSettings sharedPreferencesSettings;

    Vibrator vibrator;
    MediaPlayer mediaPlayer;

    SensorManager sensorManager;
    Sensor sensorShake;
    SensorEventListener sensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonMinus = (Button) findViewById(R.id.buttonMinus);
        buttonPlus = (Button) findViewById(R.id.buttonPlus);
        buttonSettings = (Button) findViewById(R.id.buttonSettings);
        counterValue = (EditText) findViewById(R.id.counterValue);

        sharedPreferencesSettings = SharedPreferencesSettings.getSettings(getApplicationContext());

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alert_sound);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorShake = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                float value = Math.abs(x)+Math.abs(y)+Math.abs(z);

                if(value>20){
                    sharedPreferencesSettings.currentValue = 0;
                    counterValue.setText(String.valueOf(sharedPreferencesSettings.currentValue));
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };



        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPreferencesSettings.currentValue-1 <= sharedPreferencesSettings.lowLimit){
                    sharedPreferencesSettings.currentValue = sharedPreferencesSettings.lowLimit;
                    if(sharedPreferencesSettings.lowSound){
                        mediaPlayer.start();
                    }
                    if (sharedPreferencesSettings.lowSound){
                        alertVibration();
                    }
                }
                else {
                    sharedPreferencesSettings.currentValue--;
                }
                counterValue.setText(String.valueOf(sharedPreferencesSettings.currentValue));
            }
        });

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPreferencesSettings.currentValue+1>= sharedPreferencesSettings.upLimit){
                    sharedPreferencesSettings.currentValue= sharedPreferencesSettings.upLimit;
                    if(sharedPreferencesSettings.upSound){
                        mediaPlayer.start();
                    }
                    if (sharedPreferencesSettings.upVib){
                        alertVibration();
                    }
                }
                else {
                    sharedPreferencesSettings.currentValue++;
                }
                counterValue.setText(String.valueOf(sharedPreferencesSettings.currentValue));
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        counterValue.setText(String.valueOf(sharedPreferencesSettings.currentValue));
    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferencesSettings.savedSettingsValue();
    }

    public void alertVibration(){
        if(vibrator.hasVibrator()){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                vibrator.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            vibrator.vibrate(2000);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_VOLUME_DOWN){
            if(sharedPreferencesSettings.currentValue-5<= sharedPreferencesSettings.lowLimit){
                sharedPreferencesSettings.currentValue=sharedPreferencesSettings.lowLimit;
                if(sharedPreferencesSettings.lowSound){
                    mediaPlayer.start();
                }
                if (sharedPreferencesSettings.lowSound){
                    alertVibration();
                }
            }
            else {
                sharedPreferencesSettings.currentValue-=5;
            }
            counterValue.setText(String.valueOf(sharedPreferencesSettings.currentValue));
        }
        else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            if(sharedPreferencesSettings.currentValue+5>= sharedPreferencesSettings.upLimit){
                sharedPreferencesSettings.currentValue=sharedPreferencesSettings.upLimit;
                if(sharedPreferencesSettings.upSound){
                    mediaPlayer.start();
                }
                if (sharedPreferencesSettings.upVib){
                    alertVibration();
                }
            }
            else {
                sharedPreferencesSettings.currentValue+=5;
            }
            counterValue.setText(String.valueOf(sharedPreferencesSettings.currentValue));
        }
        return true;
    }
}