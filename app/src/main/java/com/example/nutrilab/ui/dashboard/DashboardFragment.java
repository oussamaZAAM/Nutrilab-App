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

    private Map<String, Integer> nutrients;
    private TextView calories;
    private TextView carbs;
    private TextView proteins;
    private TextView fats;
    private TextView sugar;
    private TextView salt;
    private TextView fiber;

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
            nutrients = (Map<String, Integer>) args.getSerializable("nutrients"); // Retrieve the Map from the arguments
            calories.setText(Objects.requireNonNull(nutrients.get("kCalories")).toString()+" kCal");
            carbs.setText(Objects.requireNonNull(nutrients.get("carbs")).toString()+" g");
            proteins.setText(Objects.requireNonNull(nutrients.get("proteins")).toString()+" g");
            fats.setText(Objects.requireNonNull(nutrients.get("fats")).toString()+" g");
            sugar.setText(Objects.requireNonNull(nutrients.get("sugar")).toString()+" g");
            salt.setText(Objects.requireNonNull(nutrients.get("salt")).toString()+" g");
            fiber.setText(Objects.requireNonNull(nutrients.get("fiber")).toString()+" g");
        }

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.navigation_home);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}