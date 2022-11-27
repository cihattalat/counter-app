package com.example.counter_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    Button buttonUpMinus;
    Button buttonUpPlus;
    Button buttonLowMinus;
    Button buttonLowPlus;
    CheckBox checkBoxUpSound;
    CheckBox checkBoxUpVib;
    CheckBox checkBoxLowSound;
    CheckBox checkBoxLowVib;
    EditText editTextUpLimit;
    EditText editTextLowLimit;

    SharedPreferencesSettings sharedPreferencesSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferencesSettings = SharedPreferencesSettings.getSettings(getApplicationContext());

        buttonUpMinus = (Button) findViewById(R.id.buttonUpMinus);
        buttonUpPlus = (Button) findViewById(R.id.buttonUpPlus);
        buttonLowMinus = (Button) findViewById(R.id.buttonLowMinus);
        buttonLowPlus = (Button) findViewById(R.id.buttonLowPlus);

        checkBoxUpSound = (CheckBox) findViewById(R.id.checkBoxUpSound);
        checkBoxLowSound=(CheckBox) findViewById(R.id.checkBoxLowSound);
        checkBoxUpVib = (CheckBox) findViewById(R.id.checkBoxUpVib);
        checkBoxLowVib = (CheckBox) findViewById(R.id.checkBoxLowVib);

        editTextLowLimit = (EditText) findViewById(R.id.editTextLowLimit);
        editTextUpLimit = (EditText) findViewById(R.id.editTextUpLimit);

        buttonUpPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferencesSettings.upLimit++;
                editTextUpLimit.setText(String.valueOf(sharedPreferencesSettings.upLimit));
            }
        });
        buttonLowPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferencesSettings.lowLimit++;
                editTextLowLimit.setText(String.valueOf(sharedPreferencesSettings.lowLimit));
            }
        });
        buttonUpMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferencesSettings.upLimit--;
                editTextUpLimit.setText(String.valueOf(sharedPreferencesSettings.upLimit));
            }
        });
        buttonLowMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferencesSettings.lowLimit--;
                editTextLowLimit.setText(String.valueOf(sharedPreferencesSettings.lowLimit));
            }
        });

        checkBoxLowSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferencesSettings.lowSound=b;
            }
        });
        checkBoxLowVib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferencesSettings.lowVib=b;
            }
        });
        checkBoxUpSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferencesSettings.upSound=b;
            }
        });
        checkBoxUpVib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferencesSettings.upVib=b;
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferencesSettings.savedSettingsValue();
    }

    @Override
    protected void onResume() {
        super.onResume();
        editTextUpLimit.setText(String.valueOf(sharedPreferencesSettings.upLimit));
        editTextLowLimit.setText(String.valueOf(sharedPreferencesSettings.lowLimit));
        checkBoxUpVib.setChecked(sharedPreferencesSettings.upVib);
        checkBoxUpSound.setChecked(sharedPreferencesSettings.upSound);
        checkBoxLowVib.setChecked(sharedPreferencesSettings.lowVib);
        checkBoxLowSound.setChecked(sharedPreferencesSettings.lowSound);

    }
}