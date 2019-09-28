package com.pb.hackathon.util;

import android.content.SharedPreferences;

import com.pb.hackathon.MyApplication;

public class SharedPreferencesUtil {
    /**
     * The constant PREF_FILE.
     */
    public static final String PREF_FILE= "placePref.xml";

    public static void saveUser(String user){
        SharedPreferences pref = MyApplication.getAppContext().getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user", user);
        editor.commit();
    }


    public static String getUser() {
        SharedPreferences pref = MyApplication.getAppContext().getSharedPreferences(PREF_FILE, 0);
        return pref.getString("user", null); // getting String
    }

}
