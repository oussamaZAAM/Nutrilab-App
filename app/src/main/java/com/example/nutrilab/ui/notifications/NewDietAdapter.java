package com.example.nutrilab.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nutrilab.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewDietAdapter extends BaseAdapter {
    private Context mContext;
    private List<Map.Entry<String, Double>> mDataList;
    private static final String TAG = "NewDietList";

    public NewDietAdapter(Context context, Map<String, Double> NewDietList) {
        this.mContext = context;
        this.mDataList = new ArrayList<>(NewDietList.entrySet());
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_new_diet, parent, false);
        }
        TextView keyTextView = convertView.findViewById(R.id.food_name);
        TextView valueTextView = convertView.findViewById(R.id.food_grams);

        Map.Entry<String, Double> entry = mDataList.get(position);

        String key = entry.getKey();
        Object value = entry.getValue();

        keyTextView.setText(key);
        valueTextView.setText((Math.ceil((Double)value))+ " g");

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
