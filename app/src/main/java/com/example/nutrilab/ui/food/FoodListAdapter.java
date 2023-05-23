package com.example.nutrilab.ui.food;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nutrilab.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FoodListAdapter extends ArrayAdapter<String> {

    // Set to store disabled food items
    private final Set<String> disabledFoodItems;

    public FoodListAdapter(@NonNull Context context, int resource, ArrayList<String> foodList) {
        super(context, resource, foodList);
        this.disabledFoodItems = new HashSet<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String foodItem = getItem(position);

        // Inflate the layout for the list item if necessary
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.food_list_item, parent, false);
        }

        TextView foodNameTextView = convertView.findViewById(R.id.text_food_item);
        foodNameTextView.setText(foodItem);
        foodNameTextView.setTextColor(Color.BLACK);

        // Set the appearance and interactivity based on whether the food item is disabled or not
        if (disabledFoodItems.contains(foodItem)) {
            convertView.setEnabled(false);
            convertView.setAlpha(0.5f);
        } else {
            convertView.setEnabled(true);
            convertView.setAlpha(1.0f);
        }

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        String foodItem = getItem(position);
        // Check if the food item is enabled or disabled
        return !disabledFoodItems.contains(foodItem);
    }

    // Disable a specific food item
    public void disableFoodItem(String foodItem) {
        disabledFoodItems.add(foodItem);
        notifyDataSetChanged();
    }

    // Enable a specific food item
    public void enableFoodItem(String foodItem) {
        disabledFoodItems.remove(foodItem);
        notifyDataSetChanged();
    }
}

