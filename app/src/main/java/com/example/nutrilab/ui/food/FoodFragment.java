package com.example.nutrilab.ui.food;

import android.os.Bundle;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.nutrilab.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class FoodFragment extends Fragment {
    private static final String TAG = "FoodFragment";

    private FoodListAdapter foodListAdapter;
    private ArrayList<String> foodList;

    private LinearLayout stagingBox;
    private TextView selectedFoodTextView;
    private EditText gramsEditText;

    private ArrayList<String> chosenFoodList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        ListView foodListView = view.findViewById(R.id.food_list_view);
        selectedFoodTextView = view.findViewById(R.id.selected_food_text_view);
        stagingBox = view.findViewById(R.id.staging_box);
        gramsEditText = view.findViewById(R.id.grams_edit_text);
        Button confirmButton = view.findViewById(R.id.confirm_button);
        Button cancelButton = view.findViewById(R.id.cancel_button);
        ListView chosenFoodListView = view.findViewById(R.id.chosen_food_list_view);
        Button addFood = view.findViewById(R.id.add_button);

        foodList = new ArrayList<>();
        foodList.add("Apple");
        foodList.add("Banana");
        foodList.add("Carrot");
        // Add more food items here

        foodListAdapter = new FoodListAdapter(requireContext(), android.R.layout.simple_list_item_1, foodList);
        foodListView.setAdapter(foodListAdapter);

        chosenFoodList = new ArrayList<>();
        ChosenFoodListAdapter chosenFoodListAdapter = new ChosenFoodListAdapter(getContext(), chosenFoodList, foodListAdapter);
        chosenFoodListView.setAdapter(chosenFoodListAdapter);

        Bundle args = getArguments();
        if (args != null) {
            stagingBox.setVisibility(View.VISIBLE);
            selectedFoodTextView.setText(args.getString("selectedFood"));
            gramsEditText.requestFocus();
        }

        foodListView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedFood = foodList.get(position);
            stagingBox.setVisibility(View.VISIBLE);
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
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_food_list);
        });
        return view;
    }
}