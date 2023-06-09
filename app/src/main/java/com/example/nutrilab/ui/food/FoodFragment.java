package com.example.nutrilab.ui.food;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nutrilab.R;
import com.example.nutrilab.ui.general.APICallTask;
import com.example.nutrilab.ui.general.SharedPrefsHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodFragment extends Fragment {
    private static final String PREFS_NAME = "MyPrefs";
    private static final String NUTRIENTS = "nutrients";
    private FoodListAdapter foodListAdapter;

    private RelativeLayout emptyState;
    private LinearLayout stagingBox;
    private TextView selectedFoodTextView;
    private EditText gramsEditText;
    private LinearLayout generateButton;
    private TextView generateText;
    private ProgressBar generateLoading;
    private ListView chosenFoodListView;
    private final ArrayList<String> filteredList = new ArrayList<>();
    private ArrayList<Map<String, Double>> chosenFoodList;
    ArrayList<String> foodListName = new ArrayList<>();
    ArrayList<HashMap> foodList = new ArrayList<>();
    HashMap nutrientsNeeded;
    APICallTask task = new APICallTask(getParentFragment());
    public String loadJSONFromAsset() {
        String json = null;
        // Get Nutrients from Shared Preferences
        try {
            InputStream is = requireActivity().getAssets().open("FoodData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    Boolean generate = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        nutrientsNeeded = SharedPrefsHelper.loadMap(requireContext(), PREFS_NAME, NUTRIENTS);
        selectedFoodTextView = view.findViewById(R.id.selected_food_text_view);
        stagingBox = view.findViewById(R.id.staging_box);
        gramsEditText = view.findViewById(R.id.grams_edit_text);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        emptyState = view.findViewById(R.id.empty_state);
        generateButton = view.findViewById(R.id.generate_btn);
        generateText = view.findViewById(R.id.generate_text);
        generateLoading = view.findViewById(R.id.generate_loading);

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
            HashMap m_li;

            for (int i = 0; i < m_jArry.length(); i++) {

                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String Salt = jo_inside.getString("Salt");
                String Calories = jo_inside.getString("Calories");
                String Fat = jo_inside.getString("Fat");
                String Sugar = jo_inside.getString("Sugar");
                String Protein = jo_inside.getString("Protein");
                String Carbs = jo_inside.getString("Carbs");
                String name = jo_inside.getString("name");
                String Fiber = jo_inside.getString("Fiber");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<>();
                m_li.put("name", name);
                foodListName.add(name);
                m_li.put("Salt", parseDouble(Salt));
                m_li.put("Fat", parseDouble(Fat));
                m_li.put("Sugar", parseDouble(Sugar));
                m_li.put("Protein", parseDouble(Protein));
                m_li.put("Carbs", parseDouble(Carbs));
                m_li.put("Calories", parseDouble(Calories));
                m_li.put("Fiber", parseDouble(Fiber));

                foodList.add(m_li);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Add more food items here
        filteredList.addAll(foodListName);

        foodListAdapter = new FoodListAdapter(requireContext(), android.R.layout.simple_list_item_1, filteredList);
        foodListView.setAdapter(foodListAdapter);

        try {
            chosenFoodList = SharedPrefsHelper.loadArrayList(requireContext(), PREFS_NAME, "CHOSEN_FOOD");
            if (chosenFoodList.size() != 0) {
                generateButton.setVisibility(View.VISIBLE);
                for (int i=0;i<chosenFoodList.size();i++) {
                    Map<String, Double> foodName = chosenFoodList.get(i);
                    for (String key : foodName.keySet()) {
                        foodListAdapter.disableFoodItem(key);
                    }
                }
                boolean isTaskRunning = task != null && task.getStatus() == AsyncTask.Status.RUNNING;
                if(isTaskRunning){
                generateLoading.setVisibility(View.VISIBLE);
                }
            }

        } catch (Exception e) {
            chosenFoodList = new ArrayList<>();
        }

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
            if (chosenFoodList.size() != 0) {
                generateButton.setVisibility(View.VISIBLE);
            }

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
        generateButton.setOnClickListener((v) -> {
            generateText.setVisibility(View.GONE);
            generateLoading.setVisibility(View.VISIBLE);
            generate = true;
            enableAlgo(view);
        });

        confirmButton.setOnClickListener(v -> {
            String food = selectedFoodTextView.getText().toString();
            String grams = gramsEditText.getText().toString();
            if (!food.isEmpty() && !grams.isEmpty() && parseInt(grams) > 0) {
                chosenFoodList.add(new HashMap<String, Double>() {{
                    put(food, parseDouble(grams));
                }});

                try{
                    SharedPrefsHelper.saveArrayList(requireContext(), PREFS_NAME, "CHOSEN_FOOD", chosenFoodList);
                } catch (Exception ignored) {}

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

    public void checkEmptiness(ArrayList<Map<String, Double>> chosenFoodList) {
        if (chosenFoodList.size() == 0) {
            emptyState.setVisibility(View.VISIBLE);
        } else {
            emptyState.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    public void enableAlgo(View view) {
        if (generate) {
            List<Map<String, Double>> extendedChosenFoodList = new ArrayList<>();
            for (int i = 0; i < foodList.size(); i++) {
                for (int j = 0; j < chosenFoodList.size(); j++) {
                    if (foodList.get(i).get("name") == chosenFoodList.get(j).keySet().iterator().next()) {
                        foodList.get(i).put("grams", chosenFoodList.get(j).get(foodList.get(i).get("name")));
                        extendedChosenFoodList.add(foodList.get(i));
                    }

                }
            }
            Map<String, Double> nutrients = sumNutrients(extendedChosenFoodList);
            Map<String, Double> nutriRes = nutrientsNeeded;
            Map neededNutri = addValuesOfTwoObjects(nutriRes, nutrients);
            List<Map<String, java.io.Serializable>> eatenFoodNames = new ArrayList<Map<String, java.io.Serializable>>();
            int k = 0;

            task.execute(neededNutri, view, chosenFoodList, requireContext());
            for (Map<String, Double> map : chosenFoodList) {
                String key = map.keySet().iterator().next();
                Double value = map.get(key);
                eatenFoodNames.add(new HashMap<String, java.io.Serializable>());
                eatenFoodNames.get(k).put("name", key);
                eatenFoodNames.get(k).put("weight", value);

                k++;
            }
            neededNutri.put("foods", eatenFoodNames);
        } else {
            Log.d("lol", "loll");
        }
    }

    public Map<String, Double> sumObjectsByKey(Map<String, Double> object1, Map<String, Double> object2) {
        Map<String, Double> sum = new HashMap<>();
        double size = object2.get("grams") / 100.0;

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
}