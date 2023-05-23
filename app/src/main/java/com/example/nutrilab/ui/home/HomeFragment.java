package com.example.nutrilab.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.nutrilab.R;

public class HomeFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @NonNull Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Get reference to the "Get Started" button
        Button getStarted = view.findViewById(R.id.get_started);

        // Set click listener for the "Get Started" button
        getStarted.setOnClickListener(v -> {
            // Find the navigation controller
            NavController navController = Navigation.findNavController(view);
            // Navigate to the InfosFragment
            navController.navigate(R.id.navigation_infos);
        });

        return view;
    }
}
