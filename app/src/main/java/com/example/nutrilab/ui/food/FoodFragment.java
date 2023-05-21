package com.example.nutrilab.ui.food;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class FoodFragment extends Fragment {

    private static final String TAG = "FoodFragment";

    private FoodListAdapter foodListAdapter;

    private RelativeLayout emptyState;
    private LinearLayout stagingBox;
    private TextView selectedFoodTextView;
    private EditText gramsEditText;
    private Button generateButton;
    private ListView chosenFoodListView;
    private final ArrayList<String> filteredList = new ArrayList<>();
    private ArrayList<String> chosenFoodList;
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
                m_li = new HashMap<String, String>();
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
            emptyState.setVisibility(View.GONE);
            selectedFoodTextView.setText(selectedFood);
            gramsEditText.requestFocus();
            if(chosenFoodList.size()!=0){
                generateButton.setVisibility(View.GONE);
            }
        });

        confirmButton.setOnClickListener(v -> {
            String food = selectedFoodTextView.getText().toString();
            String grams = gramsEditText.getText().toString();
            if (!food.isEmpty() && !grams.isEmpty() && Integer.parseInt(grams) > 0) {
                chosenFoodList.add(food + " - " + grams + "g");
                chosenFoodListAdapter.notifyDataSetChanged();
                foodListAdapter.disableFoodItem(food);
                gramsEditText.setText("");
                stagingBox.setVisibility(View.GONE);
                generateButton.setVisibility(View.VISIBLE);
                checkEmptiness(chosenFoodList);
            }
            if(chosenFoodList.size()!=0){
                generateButton.setVisibility(View.VISIBLE);
            }
        });

        cancelButton.setOnClickListener(v -> {
            selectedFoodTextView.setText("");
            gramsEditText.setText("");
            stagingBox.setVisibility(View.GONE);
            generateButton.setVisibility(View.VISIBLE);
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

    public void checkEmptiness(ArrayList<String> chosenFoodList) {
        if (chosenFoodList.size() == 0) {
            emptyState.setVisibility(View.VISIBLE);
        } else {
            emptyState.setVisibility(View.GONE);
        }
    }
}