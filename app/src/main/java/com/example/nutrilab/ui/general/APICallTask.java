package com.example.nutrilab.ui.general;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.nutrilab.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class APICallTask extends AsyncTask<Object, Void, String> {
    private static final String PREFS_NAME = "MyPrefs";
    private static final String FOOD_DIET = "foodDiet";
    private static final String FOOD_EATEN = "foodEaten";
    NavController navController;
    @Override
    protected String doInBackground(Object... params) {
        // Perform the API call and return the response
        makeAPICall((Map) params[0], (List<Map<String,Double>>) params[2], (Context) params[3]);
        navController = Navigation.findNavController((View)params[1]);
        return "well done";
    }

    @Override
    protected void onPostExecute(String response) {
        // Process the API response in the main thread
        navController.navigate(R.id.navigation_notifications);

    }

    public void makeAPICall(Map neededNutri, List<Map<String,Double>> chosenFoodList, Context context){
        try {
            // Create a URL object with the API endpoint
            URL url = new URL("https://sbo3a.onrender.com/polls/getFood/");

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method (GET, POST, etc.)
            connection.setRequestMethod("POST");


            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            Gson gson = new Gson();
            String requestBody = gson.toJson(neededNutri);
            OutputStream outputStream =  new BufferedOutputStream(connection.getOutputStream());
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            Type type = new TypeToken<Map<String, Double>>() {}.getType();
            Map<String, Double> map = gson.fromJson(response.toString(), type);
            System.out.println(map);
            Set<String> eatenFoodSet = new HashSet<>();
            for(Map<String,Double> e:chosenFoodList){
                String key = e.keySet().iterator().next()+"_";
                map.put(key,e.get(e.keySet().iterator().next()));
                eatenFoodSet.add(key);
            }
            SharedPrefsHelper.saveMap(context, PREFS_NAME, FOOD_DIET, map);
            SharedPrefsHelper.saveSet(context, PREFS_NAME, FOOD_EATEN, eatenFoodSet);

            connection.disconnect();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}
