package com.example.nutrilab.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.nutrilab.R;
import com.example.nutrilab.databinding.FragmentDashboardBinding;

import java.util.Map;
import java.util.Objects;

public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";

    private FragmentDashboardBinding binding;

    private Map<String, Double> nutrients;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Button resetButton = view.findViewById(R.id.reset_button);
        TextView calories = view.findViewById(R.id.calories);
        TextView carbs = view.findViewById(R.id.carbs);
        TextView proteins = view.findViewById(R.id.proteins);
        TextView fats = view.findViewById(R.id.fats);
        TextView sugar = view.findViewById(R.id.sugar);
        TextView salt = view.findViewById(R.id.salt);
        TextView fiber = view.findViewById(R.id.fiber);

        Bundle args = getArguments();
        if (args != null) {
            nutrients = (Map<String, Double>) args.getSerializable("nutrients"); // Retrieve the Map from the arguments
            calories.setText(Objects.requireNonNull(nutrients.get("kCalories"))+" kCal");
            carbs.setText(Objects.requireNonNull(nutrients.get("carbs"))+" g");
            proteins.setText(Objects.requireNonNull(nutrients.get("proteins"))+" g");
            fats.setText(Objects.requireNonNull(nutrients.get("fats"))+" g");
            sugar.setText(Objects.requireNonNull(nutrients.get("sugar"))+" g");
            salt.setText(Objects.requireNonNull(nutrients.get("salt"))+" g");
            fiber.setText(Objects.requireNonNull(nutrients.get("fiber"))+" g");
        }

        resetButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_home);
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}