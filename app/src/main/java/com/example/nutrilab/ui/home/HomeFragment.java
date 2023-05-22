package com.example.nutrilab.ui.home;

import android.os.Bundle;
import android.util.Log;
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
    private static final String TAG = "HomeFragment";

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button getStarted = view.findViewById(R.id.get_started);

        getStarted.setOnClickListener(v->{
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_infos);
        });

        return view;
    }
}