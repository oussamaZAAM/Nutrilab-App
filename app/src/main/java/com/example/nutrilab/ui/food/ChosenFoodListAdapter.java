package com.example.nutrilab.ui.food;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.example.nutrilab.R;
import com.example.nutrilab.ui.general.SharedPrefsHelper;

public class ChosenFoodListAdapter extends ArrayAdapter<Map<String, Double>> {

    private static final String PREFS_NAME = "MyPrefs";

    private final LayoutInflater inflater;
    private final List<Map<String,Double>> chosenFoodList;
    private final View chosenFoodView;
    private final FoodListAdapter foodListAdapter;
    private ImageButton cancelFoodButton;

    public ChosenFoodListAdapter(Context context, List<Map<String, Double>> chosenFoodList, FoodListAdapter foodListAdapter, View chosenFoodView) {
        super(context, 0, chosenFoodList);
        this.inflater = LayoutInflater.from(context);
        this.chosenFoodList = chosenFoodList;
        this.foodListAdapter = foodListAdapter;
        this.chosenFoodView = chosenFoodView;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the list item layout if it's null
            convertView = inflater.inflate(R.layout.list_item_chosen_food, parent, false);

            // Create a ViewHolder to hold references to the views within the item layout
            viewHolder = new ViewHolder();
            viewHolder.foodNameTextView = convertView.findViewById(R.id.text_chosen_food_name);
            viewHolder.foodSizeTextView = convertView.findViewById(R.id.text_chosen_food_size);
            cancelFoodButton = convertView.findViewById(R.id.cancel_food_button);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LinearLayout listView = chosenFoodView.findViewById(R.id.generate_btn);
        RelativeLayout emptyState = chosenFoodView.findViewById(R.id.empty_state);

        // Set an OnClickListener for the cancel food button
        cancelFoodButton.setOnClickListener(v -> {
            String foodItem = chosenFoodList.get(position).keySet().iterator().next();

            // Remove the chosen food item from the list
            chosenFoodList.remove(position);

            // Save the updated chosen food list to shared preferences
            SharedPrefsHelper.saveArrayList(getContext(), PREFS_NAME, "CHOSEN_FOOD", chosenFoodList);

            // Notify the adapter that the data has changed
            notifyDataSetChanged();

            // Enable the corresponding food item in the FoodListAdapter
            foodListAdapter.enableFoodItem(foodItem);

            // Show/hide relevant views based on the number of chosen food items
            if (chosenFoodList.size() == 0) {
                listView.setVisibility(View.GONE);
                emptyState.setVisibility(View.VISIBLE);
            }
        });

        // Set the data for the item
        String food = chosenFoodList.get(position).keySet().iterator().next();
        String grams = Objects.requireNonNull(chosenFoodList.get(position).get(chosenFoodList.get(position).keySet().iterator().next())).toString();

        viewHolder.foodNameTextView.setText(food);
        viewHolder.foodSizeTextView.setText(grams + " g");

        return convertView;
    }

    static class ViewHolder {
        TextView foodNameTextView;
        TextView foodSizeTextView;
    }
}

