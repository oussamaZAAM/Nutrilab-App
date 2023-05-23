package com.example.nutrilab.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.nutrilab.R;
import com.example.nutrilab.databinding.FragmentNotificationsBinding;
import com.example.nutrilab.ui.general.SharedPrefsHelper;

public class NotificationsFragment extends Fragment {

    private static final String PREFS_NAME = "MyPrefs"; // Name for shared preferences
    private static final String FOOD_DIET = "foodDiet"; // Key for the food diet data in shared preferences
    private static final String FOOD_EATEN = "foodEaten"; // Key for the eaten food data in shared preferences

    private TextView textNotifications; // TextView for displaying notifications
    private ListView newDietListView; // ListView for displaying new diet items
    private ImageButton backButton; // ImageButton for navigating back

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        // Create a new adapter for the new diet items using the data from shared preferences
        NewDietAdapter newDietAdapter = new NewDietAdapter(requireContext(), SharedPrefsHelper.loadMap(requireContext(), PREFS_NAME, FOOD_DIET), SharedPrefsHelper.loadSet(requireContext(), PREFS_NAME, FOOD_EATEN));

        // Find the back button and set a click listener to navigate back to the food screen
        backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_food);
        });

        // Find the ListView and set the new diet adapter
        newDietListView = view.findViewById(R.id.new_diet);
        newDietListView.setAdapter(newDietAdapter);

        // Find the TextView for notifications and set the text
        textNotifications = view.findViewById(R.id.text_notifications);
        textNotifications.setText("New Diet");

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Perform any necessary cleanup or resource releasing here
    }
}
