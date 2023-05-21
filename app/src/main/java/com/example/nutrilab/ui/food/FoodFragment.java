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

import java.util.ArrayList;

public class FoodFragment extends Fragment {
    private static final String TAG = "FoodFragment";

    private FoodListAdapter foodListAdapter;
    private ArrayList<String> foodList;

    private RelativeLayout emptyState;
    private LinearLayout stagingBox;
    private TextView selectedFoodTextView;
    private EditText gramsEditText;

    private final ArrayList<String> filteredList = new ArrayList<>();
    private ArrayList<String> chosenFoodList;

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Stop daddy, I did it!");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        selectedFoodTextView = view.findViewById(R.id.selected_food_text_view);
        stagingBox = view.findViewById(R.id.staging_box);
        gramsEditText = view.findViewById(R.id.grams_edit_text);

        emptyState = view.findViewById(R.id.empty_state);

        Button confirmButton = view.findViewById(R.id.confirm_button);
        Button cancelButton = view.findViewById(R.id.cancel_button);
        ListView chosenFoodListView = view.findViewById(R.id.chosen_food_list_view);
        Button addFood = view.findViewById(R.id.add_button);
        ImageButton removeFoodList = view.findViewById(R.id.remove_food_list);
        EditText searchBar = view.findViewById(R.id.search_bar);
        ListView foodListView = view.findViewById(R.id.food_list_view);

        foodList = new ArrayList<>();
        foodList.add("Apple");
        foodList.add("Banana");
        foodList.add("Carrot");
        foodList.add("Tangerine");
        foodList.add("Pear");
        foodList.add("Strawberry");
        foodList.add("Peach");

        // Add more food items here
        filteredList.addAll(foodList);

        foodListAdapter = new FoodListAdapter(requireContext(), android.R.layout.simple_list_item_1, filteredList);
        foodListView.setAdapter(foodListAdapter);

        chosenFoodList = new ArrayList<>();
        ChosenFoodListAdapter chosenFoodListAdapter = new ChosenFoodListAdapter(requireContext(), chosenFoodList, foodListAdapter);
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
                checkEmptiness(chosenFoodList);
            }
        });

        cancelButton.setOnClickListener(v -> {
            selectedFoodTextView.setText("");
            gramsEditText.setText("");
            stagingBox.setVisibility(View.GONE);
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
            removeFoodList.setVisibility(View.VISIBLE);
            searchBar.setVisibility(View.VISIBLE);
            foodListView.setVisibility(View.VISIBLE);
            searchBar.requestFocus();
        });
        return view;
    }

    private void performSearch(String searchTerm) {
        filteredList.clear(); // Clear the existing filtered data

        for (String item : foodList) {
            if (item.toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredList.add(item);
            }
        }
        foodListAdapter.notifyDataSetChanged();
    }

    public void checkEmptiness(ArrayList<String> chosenFoodList) {
        Log.i(TAG, "Check!");
        if (chosenFoodList.size() == 0) {
            emptyState.setVisibility(View.VISIBLE);
        } else {
            emptyState.setVisibility(View.GONE);
        }
    }
}