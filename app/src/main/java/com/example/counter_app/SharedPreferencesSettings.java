package com.example.counter_app;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesSettings {
    int upLimit;
    int lowLimit;
    int currentValue;

    boolean upVib;
    boolean lowVib;
    boolean upSound;
    boolean lowSound;

    static SharedPreferencesSettings sharedPreferencesSettings = null;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private SharedPreferencesSettings(Context context){
        sharedPreferences = context.getSharedPreferences("settingsValue", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPreferencesSettings getSettings(Context context){
        if(sharedPreferencesSettings == null){
            sharedPreferencesSettings = new SharedPreferencesSettings(context);
        }
        return sharedPreferencesSettings;
    }

    public void savedSettingsValue(){
        editor.putBoolean("upVib", upVib);
        editor.putBoolean("upSound", upSound);
        editor.putBoolean("lowVib", lowVib);
        editor.putBoolean("lowSound",lowSound);
        editor.putInt("upLimit", upLimit);
        editor.putInt("lowLimit",lowLimit);
        editor.putInt("currentValue",currentValue);
    }

    public  void loadSettingsValue(){
        upVib = sharedPreferences.getBoolean("upVib",false);
        upSound = sharedPreferences.getBoolean("upSound",false);
        lowVib = sharedPreferences.getBoolean("lowVib",false);
        lowSound=sharedPreferences.getBoolean("lowSound",false);
        upLimit=sharedPreferences.getInt("upLimit", Integer.MAX_VALUE);
        lowLimit=sharedPreferences.getInt("lowLimit",Integer.MIN_VALUE);
        currentValue=sharedPreferences.getInt("currentValue",0);
    }
}
