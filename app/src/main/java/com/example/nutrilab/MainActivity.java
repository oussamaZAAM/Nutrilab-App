package com.example.nutrilab;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.nutrilab.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding; // Binding object for the main activity layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using the binding object
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Set the main activity layout
        setContentView(binding.getRoot());

        // Create an AppBarConfiguration with the specified menu IDs
        // Each menu ID represents a top-level destination
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_food)
                .build();

        // Get the navigation controller associated with this activity and the nav host fragment
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        // Set up the action bar with the navigation controller and app bar configuration
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Hide the default action bar
        getSupportActionBar().hide();

        // Set up the bottom navigation view with the navigation controller
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}
