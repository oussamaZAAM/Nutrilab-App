package com.example.nutrilab.ui.food;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.nutrilab.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class FoodFragment extends Fragment {
    private static final String TAG = "FoodFragment";

    private ListView foodListView;
    private FoodListAdapter foodListAdapter;
    private ArrayList<String> foodList;

    private LinearLayout stagingBox;
    private TextView selectedFoodTextView;
    private EditText gramsEditText;
    private Button confirmButton;
    private Button cancelButton;

    private ArrayList<String> filteredList = new ArrayList<>();
    private EditText searchBar;

    private Button addFood;
    private ListView chosenFoodListView;
    private ArrayAdapter<String> chosenFoodListAdapter;
    private ArrayList<String> chosenFoodList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        super.onCreate(savedInstanceState);

        foodListView = view.findViewById(R.id.food_list_view);
        searchBar = view.findViewById(R.id.search_bar);
        selectedFoodTextView = view.findViewById(R.id.selected_food_text_view);
        stagingBox = view.findViewById(R.id.staging_box);
        gramsEditText = view.findViewById(R.id.grams_edit_text);
        confirmButton = view.findViewById(R.id.confirm_button);
        cancelButton = view.findViewById(R.id.cancel_button);
        chosenFoodListView = view.findViewById(R.id.chosen_food_list_view);
        addFood = view.findViewById(R.id.add_button);
//
        foodList = new ArrayList<>();
        foodList.add("Apple");
        foodList.add("Banana");
        foodList.add("Carrot");
        // Add more food items here
        filteredList.addAll(foodList);


        chosenFoodList = new ArrayList<>();
        chosenFoodListAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, chosenFoodList);
        chosenFoodListView.setAdapter(chosenFoodListAdapter);
        foodListAdapter = new FoodListAdapter(getContext(), android.R.layout.simple_list_item_1, filteredList);
        foodListView.setAdapter(foodListAdapter);
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


        foodListView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedFood = foodList.get(position);
            selectedFoodTextView.setVisibility(View.VISIBLE);
            stagingBox.setVisibility(View.VISIBLE);
            gramsEditText.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.VISIBLE);
            chosenFoodListView.setVisibility(View.VISIBLE);
            addFood.setVisibility(View.VISIBLE);
            searchBar.setVisibility(View.GONE);
            foodListView.setVisibility(View.GONE);
            selectedFoodTextView.setText(selectedFood);
            gramsEditText.requestFocus();
        });


        confirmButton.setOnClickListener(v -> {
            String food = selectedFoodTextView.getText().toString();
            String grams = gramsEditText.getText().toString();
            if (!food.isEmpty() && !grams.isEmpty()) {
                chosenFoodList.add(food + " - " + grams + "g");
                chosenFoodListAdapter.notifyDataSetChanged();
//                foodListAdapter.disableFoodItem(food);
                gramsEditText.setText("");
                stagingBox.setVisibility(View.GONE);
            }
        });

        cancelButton.setOnClickListener(v -> {
            selectedFoodTextView.setText("");
            gramsEditText.setText("");
            stagingBox.setVisibility(View.GONE);
        });

        chosenFoodListView.setOnItemClickListener((parent, view12, position, id) -> {
            String foodItem = chosenFoodList.get(position).split(" - ")[0];
            chosenFoodList.remove(position);
            chosenFoodListAdapter.notifyDataSetChanged();
            foodListAdapter.enableFoodItem(foodItem);
        });

        addFood.setOnClickListener(v -> {
             selectedFoodTextView.setVisibility(View.GONE);
            stagingBox.setVisibility(View.GONE);
            gramsEditText.setVisibility(View.GONE);
            confirmButton.setVisibility(View.GONE);
            cancelButton.setVisibility(View.GONE);
            chosenFoodListView.setVisibility(View.GONE);
            addFood.setVisibility(View.GONE);
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
}