package com.example.e_voting.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPref {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    public MyPref(Context context){
        this.context = context;
        preferences  = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public MyPref(Context context, String packageName){
        this.context = context;
        preferences  = context.getSharedPreferences(packageName, Context.MODE_PRIVATE);
    }

    public void putInt(String key, Integer value){
        editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public Integer getInt(String key, Integer defaultValue){
        return preferences.getInt(key, defaultValue);
    }
    public void putString(String key, String value){
        editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public String getString(String key, String defaultValue){
        return preferences.getString(key, defaultValue);
    }
    public void putBoolean(String key, Boolean value){
        editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public Boolean getBoolean(String key, Boolean defaultValue){
        return preferences.getBoolean(key, defaultValue);
    }
    public void remove(String key){
        editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
