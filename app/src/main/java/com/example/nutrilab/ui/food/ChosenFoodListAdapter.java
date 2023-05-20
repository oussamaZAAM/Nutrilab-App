package com.example.nutrilab.ui.food;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import com.example.nutrilab.R;

public class ChosenFoodListAdapter extends ArrayAdapter<String> {
    private static final String TAG = "ChosenFoodFragment";

    private final LayoutInflater inflater;
    private final List<String> chosenFoodList;
    private final FoodListAdapter foodListAdapter;
    private ImageButton cancelFoodButton;

    public ChosenFoodListAdapter(Context context, List<String> chosenFoodList, FoodListAdapter foodListAdapter) {
        super(context, 0, chosenFoodList);
        this.inflater = LayoutInflater.from(context);
        this.chosenFoodList = chosenFoodList;
        this.foodListAdapter = foodListAdapter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_chosen_food, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.foodNameTextView = convertView.findViewById(R.id.text_chosen_food_name);
            cancelFoodButton = convertView.findViewById(R.id.cancel_food_button);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        cancelFoodButton.setOnClickListener(v -> {
            String foodItem = chosenFoodList.get(position).split(" - ")[0];
            chosenFoodList.remove(position);
            notifyDataSetChanged();
            foodListAdapter.enableFoodItem(foodItem);
        });

        // Set the data for the item
        String food = chosenFoodList.get(position);
        viewHolder.foodNameTextView.setText(food);

        return convertView;
    }

    static class ViewHolder {
        TextView foodNameTextView;
    }
}
