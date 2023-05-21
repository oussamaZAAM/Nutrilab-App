package com.example.nutrilab.ui.food;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nutrilab.R;
import com.example.nutrilab.ui.general.SharedPrefsHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.List;

public class FoodFragment extends Fragment {
    private static final String TAG = "FoodFragment";
    private static final String PREFS_NAME = "MyPrefs";
    private static final String NUTRIENTS = "nutrients";

    private FoodListAdapter foodListAdapter;

    private RelativeLayout emptyState;
    private LinearLayout stagingBox;
    private TextView selectedFoodTextView;
    private EditText gramsEditText;
    private Button generateButton;
    private ListView chosenFoodListView;
    private final ArrayList<String> filteredList = new ArrayList<>();
    private ArrayList<Map<String,Double>> chosenFoodList;
    ArrayList<String> foodListName = new ArrayList<>();
    ArrayList<HashMap<String, String>> foodList = new ArrayList<>();

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("FoodData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Stop daddy, I did it!");
    }

    public ListView getChosenFoodListView() {
        return chosenFoodListView;
    }
    Boolean generate = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        selectedFoodTextView = view.findViewById(R.id.selected_food_text_view);
        stagingBox = view.findViewById(R.id.staging_box);
        gramsEditText = view.findViewById(R.id.grams_edit_text);

        emptyState = view.findViewById(R.id.empty_state);
        generateButton = view.findViewById(R.id.generate_btn);

        Button confirmButton = view.findViewById(R.id.confirm_button);
        Button cancelButton = view.findViewById(R.id.cancel_button);
        chosenFoodListView = view.findViewById(R.id.chosen_food_list_view);
        Button addFood = view.findViewById(R.id.add_button);
        ImageButton removeFoodList = view.findViewById(R.id.remove_food_list);
        EditText searchBar = view.findViewById(R.id.search_bar);
        ListView foodListView = view.findViewById(R.id.food_list_view);


        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("NutriLab");
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {

                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String Salt = jo_inside.getString("Salt");
                String Calories = jo_inside.getString("Calories");
                String Fat = jo_inside.getString("Fat");
                String Sugar = jo_inside.getString("Sugar");
                String Protein = jo_inside.getString("Protein");
                String Carbs = jo_inside.getString("Carbs");
                String name = jo_inside.getString("name");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<>();
                m_li.put("name", name);
                foodListName.add(name);
                m_li.put("Salt", Salt);
                m_li.put("Fat", Fat);
                m_li.put("Sugar", Sugar);
                m_li.put("Protein", Protein);
                m_li.put("Carbs", Carbs);
                m_li.put("Calories", Calories);

                foodList.add(m_li);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Get Nutrients from Shared Preferences
        Map<String, Integer> nutrients = SharedPrefsHelper.loadMap(requireContext(), PREFS_NAME, NUTRIENTS);
        Log.i(TAG, "Nutrients: "+nutrients);

        // Add more food items here
        filteredList.addAll(foodListName);

        foodListAdapter = new FoodListAdapter(requireContext(), android.R.layout.simple_list_item_1, filteredList);
        foodListView.setAdapter(foodListAdapter);

        chosenFoodList = new ArrayList<>();
        ChosenFoodListAdapter chosenFoodListAdapter = new ChosenFoodListAdapter(requireContext(), chosenFoodList, foodListAdapter, view);
        chosenFoodListView.setAdapter(chosenFoodListAdapter);

        checkEmptiness(chosenFoodList);

        removeFoodList.setOnClickListener(v -> {
            chosenFoodListView.setVisibility(View.VISIBLE);
            addFood.setVisibility(View.VISIBLE);
            removeFoodList.setVisibility(View.GONE);
            searchBar.setVisibility(View.GONE);
            foodListView.setVisibility(View.GONE);
            checkEmptiness(chosenFoodList);

        });

        searchBar.requestFocus();
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(String.valueOf(searchBar.getText()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        Bundle args = getArguments();
        if (args != null) {
            stagingBox.setVisibility(View.VISIBLE);
            selectedFoodTextView.setText(args.getString("selectedFood"));
            gramsEditText.requestFocus();
        }

        foodListView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedFood = filteredList.get(position);
            selectedFoodTextView.setVisibility(View.VISIBLE);
            stagingBox.setVisibility(View.VISIBLE);
            gramsEditText.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.VISIBLE);
            chosenFoodListView.setVisibility(View.VISIBLE);
            addFood.setVisibility(View.VISIBLE);
            removeFoodList.setVisibility(View.GONE);
            searchBar.setVisibility(View.GONE);
            foodListView.setVisibility(View.GONE);
            selectedFoodTextView.setText(selectedFood);
            gramsEditText.requestFocus();
            if (chosenFoodList.size() != 0) {
                generateButton.setVisibility(View.GONE);
            }
        });
        generateButton.setOnClickListener((v)->{
            generate=true;
        });
        confirmButton.setOnClickListener(v -> {
            String food = selectedFoodTextView.getText().toString();
            String grams = gramsEditText.getText().toString();
            if (!food.isEmpty() && !grams.isEmpty() && parseInt(grams) > 0) {
                chosenFoodList.add(new HashMap<String, Double>() {{
                    put(food, parseDouble(grams));
                }});

                chosenFoodListAdapter.notifyDataSetChanged();
                foodListAdapter.disableFoodItem(food);
                gramsEditText.setText("");
                stagingBox.setVisibility(View.GONE);
                generateButton.setVisibility(View.VISIBLE);
                checkEmptiness(chosenFoodList);
            }
            if (chosenFoodList.size() != 0) {
                generateButton.setVisibility(View.VISIBLE);
            }
        });

        cancelButton.setOnClickListener(v -> {
            selectedFoodTextView.setText("");
            gramsEditText.setText("");
            stagingBox.setVisibility(View.GONE);
            if (chosenFoodList.size() != 0) {
                generateButton.setVisibility(View.VISIBLE);
            }
            checkEmptiness(chosenFoodList);
        });

        addFood.setOnClickListener(v -> {
            selectedFoodTextView.setVisibility(View.GONE);
            stagingBox.setVisibility(View.GONE);
            gramsEditText.setVisibility(View.GONE);
            confirmButton.setVisibility(View.GONE);
            cancelButton.setVisibility(View.GONE);
            chosenFoodListView.setVisibility(View.GONE);
            addFood.setVisibility(View.GONE);
            emptyState.setVisibility(View.GONE);
            generateButton.setVisibility(View.GONE);
            removeFoodList.setVisibility(View.VISIBLE);
            searchBar.setVisibility(View.VISIBLE);
            foodListView.setVisibility(View.VISIBLE);
            searchBar.requestFocus();
        });
        return view;
    }

    private void performSearch(String searchTerm) {
        filteredList.clear(); // Clear the existing filtered data

        for (String item : foodListName) {
            if (item.toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredList.add(item);
            }
        }
        foodListAdapter.notifyDataSetChanged();
    }

    public void checkEmptiness(ArrayList<Map<String,Double>> chosenFoodList) {
        if (chosenFoodList.size() == 0) {
            emptyState.setVisibility(View.VISIBLE);
        } else {
            emptyState.setVisibility(View.GONE);
        }
    }

    public static Map<String, Double> addValuesOfTwoObjects(Map<String, Double> obj1, Map<String, Double> obj2) {
        Map<String, Double> obj3 = new HashMap<>();

        obj3.put("Calories", obj1.get("kCalories") - obj2.get("Calories"));
        obj3.put("Protein", obj1.get("proteins") - obj2.get("Protein"));
        obj3.put("Carbs", obj1.get("carbs") - obj2.get("Carbs"));
        obj3.put("Fat", obj1.get("fats") - obj2.get("Fat"));
        obj3.put("Fiber", obj1.get("fiber") - obj2.get("Fiber"));
        obj3.put("Salt", obj1.get("salt") - obj2.get("Salt"));
        obj3.put("Sugar", obj1.get("sugar") - obj2.get("Sugar"));

        return obj3;
    }

    public void enableAlgo() {
        if (generate) {


            Map<String, Double> nutrients = sumNutrients(chosenFoodList);
            Map<String, Double> nutriRes =new HashMap<String, Double>() {
                {put("ayoub",(double)66);}};
            Map neededNutri = addValuesOfTwoObjects(nutriRes, nutrients);
            List<Map<String,Double>> eatenFoodNames = chosenFoodList;
            neededNutri.put("foods", eatenFoodNames);
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
                // Create the request payload


                // Write the request payload to the connection's output stream
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(requestBody.getBytes());
                outputStream.flush();
                outputStream.close();

                // Get the response code
                int responseCode = connection.getResponseCode();
                System.out.println("Response Code: " + responseCode);

                // Read the response from the API
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Print the response
                System.out.println("Response: " + response.toString());

                // Disconnect the connection
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
//            alert("Please insert your informations");
//            router.push("#yourInformations");
            Log.d("lol","loll");
        }
    }

    public Map<String, Double> sumObjectsByKey(Map<String, Double> object1, Map<String, Double> object2) {
        Map<String, Double> sum = new HashMap<>();
        double size = parseDouble(object2.get("size").toString()) / 100.0;

        sum.put("Calories", object1.get("Calories") + (object2.get("Calories") * size));
        sum.put("Protein", object1.get("Protein") + (object2.get("Protein") * size));
        sum.put("Carbs", object1.get("Carbs") + (object2.get("Carbs") * size));
        sum.put("Fat", object1.get("Fat") + (object2.get("Fat") * size));
        sum.put("Fiber", object1.get("Fiber") + (object2.get("Fiber") * size));
        sum.put("Salt", object1.get("Salt") + (object2.get("Salt") * size));
        sum.put("Sugar", object1.get("Sugar") + (object2.get("Sugar") * size));

        return sum;
    }

    public Map<String, Double> sumNutrients(List<Map<String, Double>> eatenFoodList) {
        Map<String, Double> nutrients = new HashMap<>();
        nutrients.put("Calories", 0.0);
        nutrients.put("Protein", 0.0);
        nutrients.put("Carbs", 0.0);
        nutrients.put("Fat", 0.0);
        nutrients.put("Fiber", 0.0);
        nutrients.put("Salt", 0.0);
        nutrients.put("Sugar", 0.0);

        for (Map<String, Double> food : eatenFoodList) {
            nutrients = sumObjectsByKey(nutrients, food);
        }

        return nutrients;
    }

    public static List<String> extractKeys(List<Map<String, Double>> list) {
        List<String> keysList = new ArrayList<>();
        for (Map<String, Double> map : list) {
            String food = map.keySet().iterator().next();
            keysList.add(food);

        }
        return keysList;
    }
}