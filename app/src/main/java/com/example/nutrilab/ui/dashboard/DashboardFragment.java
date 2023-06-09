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

    private FragmentDashboardBinding binding;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        // Inflate the layout for this fragment using ViewBinding
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
            // Retrieve the nutrient values from the arguments and display them in the corresponding TextViews
            Map<String, Double> nutrients = (Map<String, Double>) args.getSerializable("nutrients");
            calories.setText(Objects.requireNonNull(nutrients.get("kCalories")) + " kCal");
            carbs.setText(Objects.requireNonNull(nutrients.get("carbs")) + " g");
            proteins.setText(Objects.requireNonNull(nutrients.get("proteins")) + " g");
            fats.setText(Objects.requireNonNull(nutrients.get("fats")) + " g");
            sugar.setText(Objects.requireNonNull(nutrients.get("sugar")) + " g");
            salt.setText(Objects.requireNonNull(nutrients.get("salt")) + " g");
            fiber.setText(Objects.requireNonNull(nutrients.get("fiber")) + " g");
        }

        resetButton.setOnClickListener(v -> {
            // Navigate back to the HomeFragment when the reset button is clicked
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_infos);
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Release the ViewBinding
        binding = null;
    }
}
