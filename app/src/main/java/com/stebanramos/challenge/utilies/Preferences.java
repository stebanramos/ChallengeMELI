package com.stebanramos.challenge.utilies;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Preferences {

    private static final String TAG = "Preferences";

    private static final String KEY_PREFERENCE ="PREFERENCE_FILE_KEY";

    public static void Set_str(Context contexto, String name, String value) {
        try {
            Log.i(TAG, "SET_STR: " + name + " -> " + value);
            SharedPreferences reader = contexto.getSharedPreferences(KEY_PREFERENCE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = reader.edit();
            editor.putString(name, value);
            editor.apply();
        } catch (Exception e) {
        }
    }

    public static String Get_str(Context contexto, String key) {
        String result = "";
        try {
            SharedPreferences reader = contexto.getSharedPreferences(KEY_PREFERENCE, Context.MODE_PRIVATE);
            result = reader.getString(key, "");
            Log.i(TAG, "GET_STRING key = " + key + " = " + result);
        } catch (Exception e) {
        }

        return result;
    }
}
