package com.example.nutrilab.ui.general;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SharedPrefsHelper {

    // Save a map of String keys and Double values to SharedPreferences
    public static void saveMap(Context context, String PREFS_NAME, String MAP_KEY, Map<String, Double> map) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Convert the map to JSON using Gson
        Gson gson = new Gson();
        String mapJson = gson.toJson(map);

        // Save the JSON string to SharedPreferences
        editor.putString(MAP_KEY, mapJson);
        editor.apply();
    }

    // Load a map of String keys and Double values from SharedPreferences
    public static HashMap loadMap(Context context, String PREFS_NAME, String MAP_KEY) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Get the JSON string from SharedPreferences
        String mapJson = sharedPreferences.getString(MAP_KEY, null);

        if (mapJson != null) {
            // Convert the JSON string to a map using Gson
            Gson gson = new Gson();
            return gson.fromJson(mapJson, HashMap.class);
        }

        return new HashMap<>();
    }

    // Save an Integer value to SharedPreferences
    public static void saveInteger(Context context, String PREFS_NAME, String KEY, Integer value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY, value);
        editor.apply();
    }

    // Load an Integer value from SharedPreferences
    public static Integer loadInteger(Context context, String PREFS_NAME, String KEY) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY, 0);
    }

    // Save a Set value to SharedPreferences
    public static void saveSet(Context context, String PREFS_NAME, String KEY, Set value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putStringSet(KEY, value);
        editor.apply();
    }

    // Load a Set value from SharedPreferences
    public static Set loadSet(Context context, String PREFS_NAME, String KEY) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet(KEY, null);
    }

    // Save a String value to SharedPreferences
    public static void saveString(Context context, String PREFS_NAME, String KEY, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY, value);
        editor.apply();
    }

    // Load a String value from SharedPreferences
    public static String loadString(Context context, String PREFS_NAME, String KEY) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY, null);
    }

    // Save a Float value to SharedPreferences
    public static void saveFloat(Context context, String PREFS_NAME, String KEY, Float value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putFloat(KEY, value);
        editor.apply();
    }

    // Load a Float value from SharedPreferences
    public static Float loadFloat(Context context, String PREFS_NAME, String KEY) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(KEY, 0);
    }

    // Save an ArrayList of maps to SharedPreferences
    public static void saveArrayList(Context context, String PREFS_NAME, String KEY, List<Map<String, Double>> array) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Convert the ArrayList to JSON using Gson
        Gson gson = new Gson();
        Type type = new TypeToken<List<Map<String, Double>>>() {}.getType();
        String json = gson.toJson(array, type);

        // Save the JSON string to SharedPreferences
        editor.putString(KEY, json);
        editor.apply();
    }

    // Load an ArrayList of maps from SharedPreferences
    public static ArrayList<Map<String, Double>> loadArrayList(Context context, String PREFS_NAME, String KEY) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Get the JSON string from SharedPreferences
        String json = sharedPreferences.getString(KEY, null);
        if (json != null) {
            // Convert the JSON string to an ArrayList of maps using Gson
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Map<String, Double>>>() {}.getType();
            return gson.fromJson(json, type);
        }

        return new ArrayList<>();
    }
}

