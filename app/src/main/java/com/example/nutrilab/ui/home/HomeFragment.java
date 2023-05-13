package com.example.nutrilab.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.nutrilab.R;

public class HomeFragment extends Fragment {

    private EditText ageEditText;
    private TextView ageTitle;

    private RadioGroup genderRadioGroup;
    private EditText heightEditText;
    private EditText weightEditText;
    private Button nextButton;
    private Button backButton;

    private int age;
    private String gender;
    private float height;
    private float weight;

    private boolean isAgeInterface = true;

    @SuppressLint("CutPasteId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ageEditText = view.findViewById(R.id.ageEditText);
        ageTitle = view.findViewById(R.id.ageTitle);

        genderRadioGroup = view.findViewById(R.id.genderRadioGroup);
        heightEditText = view.findViewById(R.id.heightEditText);
        weightEditText = view.findViewById(R.id.weightEditText);
        nextButton = view.findViewById(R.id.nextButton);
        backButton = view.findViewById(R.id.backButton);

        EditText myEditText = view.findViewById(R.id.ageEditText);
        myEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    myEditText.setBackgroundResource(R.drawable.edittext_border);
                } else {
                    myEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ageEditText.getVisibility() == View.VISIBLE) {
                    // Get the value of the age field and store it in a variable
                    age = Integer.parseInt(ageEditText.getText().toString());

                    ageTitle.setText("What's your gender");

                    // Hide the age field and show the gender field
                    ageEditText.setVisibility(View.GONE);
                    genderRadioGroup.setVisibility(View.VISIBLE);
                    backButton.setVisibility(View.VISIBLE);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genderRadioGroup.getVisibility() == View.VISIBLE) {
                    // Show the age field and hide the gender field
                    ageEditText.setVisibility(View.VISIBLE);
                    genderRadioGroup.setVisibility(View.GONE);
                    backButton.setVisibility(View.GONE);
                    ageTitle.setText("How old are you?");
                }
//                heightEditText.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}