package com.example.nutrilab.ui.general;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import androidx.fragment.app.Fragment;
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
import java.lang.ref.WeakReference;
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
    private final WeakReference<Fragment> fragmentRef;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String FOOD_DIET = "foodDiet";
    private static final String FOOD_EATEN = "foodEaten";
    NavController navController;

    public APICallTask(Fragment fragment) {
        // Create a weak reference to the fragment to avoid memory leaks
        fragmentRef = new WeakReference<>(fragment);
    }

    @Override
    protected String doInBackground(Object... params) {
        // Perform the API call and return the response
        makeAPICall((Map) params[0], (List<Map<String,Double>>) params[2], (Context) params[3]);
            navController = Navigation.findNavController((View)params[1]);
        return "well done";
    }

    @Override
    protected void onPostExecute(String response) {
        // Get the fragment from the weak reference
            // Process the API response in the main thread
            navController.navigate(R.id.navigation_notifications);

    }

    public void makeAPICall(Map neededNutri, List<Map<String,Double>> chosenFoodList, Context context) {
        try {
            // Create a URL object with the API endpoint
            URL url = new URL("https://nutrilab-api.up.railway.app/polls/getFood/");

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method (GET, POST, etc.)
            connection.setRequestMethod("POST");

            // Enable output and set the content type
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // Convert the neededNutri map to JSON
            Gson gson = new Gson();
            String requestBody = gson.toJson(neededNutri);

            // Write the request body to the connection's output stream
            OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            // Read the API response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the API response into a map
            Type type = new TypeToken<Map<String, Double>>() {}.getType();
            Map<String, Double> map = gson.fromJson(response.toString(), type);
            System.out.println(map);

            // Prepare the eatenFoodSet and update the map with chosen foods
            Set<String> eatenFoodSet = new HashSet<>();
            for (Map<String, Double> e : chosenFoodList) {
                String key = e.keySet().iterator().next() + "_";
                map.put(key, e.get(e.keySet().iterator().next()));
                eatenFoodSet.add(key);
            }

            // Save the updated map and eatenFoodSet to shared preferences
            SharedPrefsHelper.saveMap(context, PREFS_NAME, FOOD_DIET, map);
            SharedPrefsHelper.saveSet(context, PREFS_NAME, FOOD_EATEN, eatenFoodSet);

            // Disconnect the connection
            connection.disconnect();
        } catch (ProtocolException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

