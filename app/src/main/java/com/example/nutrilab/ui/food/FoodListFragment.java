package com.example.nutrilab.ui.food;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nutrilab.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;


public class FoodListFragment extends Fragment {

    private ArrayList<String> foodList;


    private ArrayAdapter<String> chosenFoodListAdapter;
    private ArrayList<String> chosenFoodList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);

        ListView foodListView = view.findViewById(R.id.food_list_view);
//        selectedFoodTextView = view.findViewById(R.id.selected_food_text_view);
//        stagingBox = view.findViewById(R.id.staging_box);
//        gramsEditText = view.findViewById(R.id.grams_edit_text);
//        confirmButton = view.findViewById(R.id.confirm_button);
//        cancelButton = view.findViewById(R.id.cancel_button);
//        chosenFoodListView = view.findViewById(R.id.chosen_food_list_view);

        foodList = new ArrayList<>();
        foodList.add("Apple");
        foodList.add("Banana");
        foodList.add("Carrot");
        // Add more food items here

        FoodListAdapter foodListAdapter = new FoodListAdapter(requireContext(), android.R.layout.simple_list_item_1, foodList);
        foodListView.setAdapter(foodListAdapter);


        foodListView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedFood = foodList.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("selectedFood", selectedFood);

            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_food, bundle);
        });



        return view;
    }
}