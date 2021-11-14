package com.example.onlinemedicalstore;

import android.content.Context;
import android.content.SharedPreferences;

// class to save data in mobile memory

public class SharedPref {
    private static com.example.onlinemedicalstore.SharedPref mInstance;
    private static SharedPreferences mSharedPreferences;
    private static Context mCtx;
    private static final String pref_name = "mysharedpref";


    private SharedPref(Context context) {
        mCtx = context;
    }
    public static synchronized com.example.onlinemedicalstore.SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new com.example.onlinemedicalstore.SharedPref(context);
        }
        return mInstance;
    }


    public static boolean setUserType(String type, Context mContext) {
        mSharedPreferences = mContext.getSharedPreferences(pref_name, 0);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("type", type);
        return editor.commit();
    }

    public static String getUserType(Context mContext) {
        mSharedPreferences = mContext.getSharedPreferences(pref_name, 0);
        return mSharedPreferences.getString("type", null);
    }

}
