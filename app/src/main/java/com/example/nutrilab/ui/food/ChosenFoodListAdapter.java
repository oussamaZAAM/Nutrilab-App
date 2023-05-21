package com.example.nutrilab.ui.food;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;

import com.example.nutrilab.R;

public class ChosenFoodListAdapter extends ArrayAdapter<Map<String, Double>> {
    private static final String TAG = "ChosenFoodFragment";

    private final LayoutInflater inflater;
    private final List<Map<String,Double>> chosenFoodList;
    private final FoodListAdapter foodListAdapter;
    private ImageButton cancelFoodButton;
    private View chosenFoodView;
    public ChosenFoodListAdapter(Context context, List<Map<String, Double>> chosenFoodList, FoodListAdapter foodListAdapter, View chosenFoodView) {
        super(context, 0, chosenFoodList);
        this.inflater = LayoutInflater.from(context);
        this.chosenFoodList = chosenFoodList;
        this.foodListAdapter = foodListAdapter;
        this.chosenFoodView = chosenFoodView;
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
        Button listView = chosenFoodView.findViewById(R.id.generate_btn);
        cancelFoodButton.setOnClickListener(v -> {
            String key = chosenFoodList.get(position).keySet().iterator().next();
            String foodItem = key;
            chosenFoodList.remove(position);
            notifyDataSetChanged();
            foodListAdapter.enableFoodItem(foodItem);
            if(chosenFoodList.size()==0){
                listView.setVisibility(View.GONE);
            }
        });

        // Set the data for the item
        String food = chosenFoodList.get(position).keySet().iterator().next();
        viewHolder.foodNameTextView.setText(food);

        return convertView;
    }

    static class ViewHolder {
        TextView foodNameTextView;
    }
}
