package com.example.nutrilab.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nutrilab.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NewDietAdapter extends BaseAdapter {

    private final Context mContext; // Context reference
    private final Set eatenFoodSet; // Set to track eaten food items
    private final List<Map.Entry<String, Double>> mDataList; // List of data entries
    private static final String TAG = "NewDietList"; // Tag for logging

    /**
     * Constructor for the NewDietAdapter class.
     *
     * @param context      The context of the adapter.
     * @param newDietList  The map of new diet items.
     * @param eatenFoodSet The set of eaten food items.
     */
    public NewDietAdapter(Context context, Map<String, Double> newDietList, Set eatenFoodSet) {
        this.mContext = context;
        this.mDataList = new ArrayList<>(newDietList.entrySet());
        this.eatenFoodSet = eatenFoodSet;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Gets the view for a specific item in the list.
     *
     * @param position    The position of the item in the list.
     * @param convertView The recycled view to populate.
     * @param parent      The parent view group.
     * @return The populated view for the item.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // If no recycled view available, inflate the layout for the list item
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_new_diet, parent, false);
        }

        TextView keyTextView = convertView.findViewById(R.id.food_name);
        TextView valueTextView = convertView.findViewById(R.id.food_grams);
        TextView valueTextOpView = convertView.findViewById(R.id.food_grams_op);

        Map.Entry<String, Double> entry = mDataList.get(position);
        String key;

        if (eatenFoodSet.contains(entry.getKey())) {
            if (entry.getValue() > 0) {
                valueTextOpView.setTextColor(Color.BLACK);
                key = entry.getKey().substring(0, entry.getKey().length() - 1);
                valueTextOpView.setVisibility(View.GONE);
            } else {
                valueTextOpView.setTextColor(Color.RED);
                key = entry.getKey().substring(0, entry.getKey().length() - 1);
                valueTextOpView.setText("(-" + (int) Math.ceil((Double) entry.getValue()) + ")");
            }
        } else {
            valueTextOpView.setTextColor(Color.rgb(28, 178, 73));
            key = entry.getKey();
            valueTextOpView.setText("(+" + (int) Math.ceil((Double) entry.getValue()) + ")");
        }

        Double value = entry.getValue();
        keyTextView.setText(key);
        valueTextView.setText(((int) Math.ceil(value)) + " g");

        return convertView;
    }

    //    private final LayoutInflater inflater;
//    private final Map<String,Double> NewDietList;
//    private final View newDietView;
//    private ImageButton cancelFoodButton;
//    public NewDietAdapter(Context context, Map<String, Double> NewDietList,  View newDietView) {
//
//        super(context, 0, NewDietList);
//        this.inflater = LayoutInflater.from(context);
//        this.NewDietList = NewDietList;
//        this.newDietView = newDietView;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        ViewHolder viewHolder;
//
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.list_item_chosen_food, parent, false);
//
//            viewHolder = new ViewHolder();
//            viewHolder.foodNameTextView = convertView.findViewById(R.id.text_chosen_food_name);
//            viewHolder.foodSizeTextView = convertView.findViewById(R.id.text_chosen_food_size);
//            cancelFoodButton = convertView.findViewById(R.id.cancel_food_button);
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        LinearLayout listView = chosenFoodView.findViewById(R.id.generate_btn);
//        RelativeLayout emptyState = chosenFoodView.findViewById(R.id.empty_state);
//        cancelFoodButton.setOnClickListener(v -> {
//            String foodItem = chosenFoodList.get(position).keySet().iterator().next();
//            chosenFoodList.remove(position);
//            notifyDataSetChanged();
//            foodListAdapter.enableFoodItem(foodItem);
//            if (chosenFoodList.size() == 0) {
//                listView.setVisibility(View.GONE);
//                emptyState.setVisibility(View.VISIBLE);
//            }
//        });
//
//        // Set the data for the item
//        String food = chosenFoodList.get(position).keySet().iterator().next();
//        String grams = chosenFoodList.get(position).get(chosenFoodList.get(position).keySet().iterator().next()).toString();
//
//        viewHolder.foodNameTextView.setText(food);
//        viewHolder.foodSizeTextView.setText(grams+" g");
//
//        return convertView;
//    }
//
//    static class ViewHolder {
//        TextView foodNameTextView;
//        TextView foodSizeTextView;
//    }
}

