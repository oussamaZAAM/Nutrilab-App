package com.example.nutrilab.ui.general;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SharedPrefsHelper {

    public static void saveMap(Context context, String PREFS_NAME, String MAP_KEY, Map<String, Double> map) {
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

    public static void saveInteger(Context context, String PREFS_NAME, String KEY, Integer value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY, value);
        editor.apply();
    }

    public static Integer loadInteger(Context context, String PREFS_NAME, String KEY) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY, 0);
    }

    public static void saveSet(Context context, String PREFS_NAME, String KEY, Set value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putStringSet(KEY, value);
        editor.apply();
    }

    public static Set loadSet(Context context, String PREFS_NAME, String KEY) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet(KEY, null);
    }

    public static void saveString(Context context, String PREFS_NAME, String KEY, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY, value);
        editor.apply();
    }

    public static String loadString(Context context, String PREFS_NAME, String KEY) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY, null);
    }

    public static void saveFloat(Context context, String PREFS_NAME, String KEY, Float value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putFloat(KEY, value);
        editor.apply();
    }

    public static Float loadFloat(Context context, String PREFS_NAME, String KEY) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(KEY, 0);
    }

    public static void saveArrayList(Context context, String PREFS_NAME, String KEY, List<Map<String, Double>> array) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        Type type = new TypeToken<List<Map<String, Double>>>() {}.getType();
        String json = gson.toJson(array, type);

        editor.putString(KEY, json);
        editor.apply();
    }

    public static ArrayList<Map<String, Double>> loadArrayList(Context context, String PREFS_NAME, String KEY) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String json = sharedPreferences.getString(KEY, null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Map<String, Double>>>() {}.getType();
            return gson.fromJson(json, type);
        }

        return new ArrayList<>();
    }

}
