package com.example.nutrilab.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nutrilab.R;
import com.example.nutrilab.databinding.FragmentNotificationsBinding;
import com.example.nutrilab.ui.food.FoodListAdapter;
import com.example.nutrilab.ui.general.SharedPrefsHelper;

import java.util.HashMap;
import java.util.Map;

public class NotificationsFragment extends Fragment {
    private static final String PREFS_NAME = "MyPrefs";
    private static final String FOOD_DIET = "foodDiet";
    private static final String FOOD_EATEN = "foodEaten";
    private TextView textNotifications;
    private ListView newDietListView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        NewDietAdapter newDietAdapter = new NewDietAdapter(requireContext(), SharedPrefsHelper.loadMap(requireContext(), PREFS_NAME, FOOD_DIET), SharedPrefsHelper.loadSet(requireContext(), PREFS_NAME, FOOD_EATEN));

        newDietListView = view.findViewById(R.id.new_diet);
        newDietListView.setAdapter(newDietAdapter);
        textNotifications = view.findViewById(R.id.text_notifications);
        textNotifications.setText("New Diet");
       return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}