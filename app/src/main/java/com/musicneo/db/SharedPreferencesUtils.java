package com.musicneo.db;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {


    private SharedPreferences sharedPreference;

    public SharedPreferencesUtils(Context mContext) {
        sharedPreference = mContext.getSharedPreferences(mContext.getPackageName() + "_preferences", Context.MODE_PRIVATE);
    }

    public void clearAll(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences(mContext.getPackageName() + "_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
    public boolean getBoolean(String key) {
        return sharedPreference.getBoolean(key,false);
    }

    public void setBoolean(String key ,Boolean v) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(key,v);
        editor.commit();
    }
}
