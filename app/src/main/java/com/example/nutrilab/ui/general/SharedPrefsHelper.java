package com.example.nutrilab.ui.general;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SharedPrefsHelper {

    public static void saveMap(Context context, String PREFS_NAME, String MAP_KEY, Map<String,Double> map) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String mapJson = gson.toJson(map);

        editor.putString(MAP_KEY, mapJson);
        editor.apply();
    }

    public static Map<String, Double> loadMap(Context context, String PREFS_NAME, String MAP_KEY) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String mapJson = sharedPreferences.getString(MAP_KEY, null);

        if (mapJson != null) {
            Gson gson = new Gson();
            return gson.fromJson(mapJson, HashMap.class);
        }

        return new HashMap<>();
    }
}
