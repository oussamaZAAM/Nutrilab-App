package com.example.nutrilab.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.nutrilab.R;

import java.math.BigDecimal;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private EditText ageEditText;
    private TextView infoTitle;

    private LinearLayout genderChoice;
    private ImageView maleIcon;
    private ImageView femaleIcon;

    private EditText heightEditText;
    private EditText weightEditText;
    private TextView heightTitle;
    private TextView weightTitle;

    private Button nextButton;
    private Button backButton;

    private LinearLayout activity;
    private Spinner activitySpinner;

    private int age;
    private String gender;
    private float height;
    private float weight;

    public boolean isAgeActivated;
    public boolean isGenderActivated;
    public boolean isHeightActivated;
    public boolean isActivityActivated;

    @SuppressLint({"CutPasteId", "ClickableViewAccessibility"})
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Populate data inside Activity dropdown menu
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.activities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ageEditText = view.findViewById(R.id.ageEditText);
        infoTitle = view.findViewById(R.id.infosTitle);

        genderChoice = view.findViewById(R.id.genderChoice);
        maleIcon = view.findViewById(R.id.maleIcon);
        femaleIcon = view.findViewById(R.id.femaleIcon);

        heightEditText = view.findViewById(R.id.heightEditText);
        weightEditText = view.findViewById(R.id.weightEditText);
        heightTitle = view.findViewById(R.id.heightTitle);
        weightTitle = view.findViewById(R.id.weightTitle);

        activitySpinner = view.findViewById(R.id.activitySpinner);
        activitySpinner.setAdapter(adapter);
        activity = view.findViewById(R.id.activity);

        nextButton = view.findViewById(R.id.nextButton);
        backButton = view.findViewById(R.id.backButton);

        isAgeActivated = true;
        isGenderActivated = isHeightActivated = isActivityActivated = false;

        // Event Listeners

        ageEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ageEditText.setBackgroundResource(R.drawable.edittext_border);
                } else {
                    ageEditText.setBackgroundResource(R.drawable.edittext_focused);
                }
            }
        });

        maleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "male";
                if (gender == "male"){
                    maleIcon.setBackgroundResource(R.drawable.male_background);
                    femaleIcon.setBackgroundResource(0);
                }
            }
        });

        femaleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "female";
                if (gender == "female"){
                    maleIcon.setBackgroundResource(0);
                    femaleIcon.setBackgroundResource(R.drawable.female_background);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ageEditText.getVisibility() == View.VISIBLE) {
                    // Get the value of the age field and store it in a variable
                    age = Integer.parseInt(ageEditText.getText().toString());

                    if (age >= 12 && age <= 120){
                        // Hide the age field and show the gender field
                        isAgeActivated = false;
                        isGenderActivated = true;
                    }
                }
                if (genderChoice.getVisibility() == View.VISIBLE) {
                    if (gender == "male" || gender == "female"){
                        isGenderActivated = false;
                        isHeightActivated = true;
                    }
                }
                if (heightEditText.getVisibility() == View.VISIBLE && weightEditText.getVisibility() == View.VISIBLE) {
                    try {
                        height = Float.parseFloat(heightEditText.getText().toString());
                    } catch (NumberFormatException e) {
                        height = 0;
                    }
                    try {
                        weight = Float.parseFloat(weightEditText.getText().toString());
                    } catch (NumberFormatException e) {
                        weight = 0;
                    }

                    if ((height >= 80 && height <= 300) && (weight >= 20 && weight <= 200)){
                        isHeightActivated = false;
                        isActivityActivated = true;
                    }
                }


                if (isAgeActivated) {
                        ageEditText.setVisibility(View.VISIBLE);
                        infoTitle.setText("How old are you?");
                        backButton.setVisibility(View.INVISIBLE);
                    } else {
                        ageEditText.setVisibility(View.GONE);
                        backButton.setVisibility(View.VISIBLE);
                    }
                if (isGenderActivated) {
                        genderChoice.setVisibility(View.VISIBLE);
                        infoTitle.setText("What's your gender?");
                    } else {
                        genderChoice.setVisibility(View.GONE);
                    }
                if (isHeightActivated) {
                        heightEditText.setVisibility(View.VISIBLE);
                        weightEditText.setVisibility(View.VISIBLE);
                        heightTitle.setVisibility(View.VISIBLE);
                        weightTitle.setVisibility(View.VISIBLE);
                        infoTitle.setText("What's your Height & Weight?");
                    } else {
                        heightEditText.setVisibility(View.GONE);
                        weightEditText.setVisibility(View.GONE);
                        heightTitle.setVisibility(View.GONE);
                        weightTitle.setVisibility(View.GONE);
                    }
                if (isActivityActivated) {
                        activity.setVisibility(View.VISIBLE);
                        infoTitle.setText("How is your Activity?");
                    } else {
                        activity.setVisibility(View.GONE);
                    }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genderChoice.getVisibility() == View.VISIBLE) {
                    // Show the age field and hide the gender field
                    isGenderActivated = false;
                    isAgeActivated = true;
                }
                if (heightEditText.getVisibility() == View.VISIBLE && weightEditText.getVisibility() == View.VISIBLE) {
                    // Show the gender field and hide the h/weight field
                    isHeightActivated = false;
                    isGenderActivated = true;
                    infoTitle.setText("What's your gender?");
                }
                if (activity.getVisibility() == View.VISIBLE) {
                    isActivityActivated = false;
                    isHeightActivated = true;
                    infoTitle.setText("What's your Height & Weight?");
                }


                if (isAgeActivated) {
                        ageEditText.setVisibility(View.VISIBLE);
                        infoTitle.setText("How old are you?");
                        backButton.setVisibility(View.INVISIBLE);
                    } else {
                        ageEditText.setVisibility(View.GONE);
                        backButton.setVisibility(View.VISIBLE);
                    }
                if (isGenderActivated) {
                        genderChoice.setVisibility(View.VISIBLE);
                        infoTitle.setText("What's your gender?");
                    } else {
                        genderChoice.setVisibility(View.GONE);
                    }
                if (isHeightActivated) {
                        heightEditText.setVisibility(View.VISIBLE);
                        weightEditText.setVisibility(View.VISIBLE);
                        heightTitle.setVisibility(View.VISIBLE);
                        weightTitle.setVisibility(View.VISIBLE);
                        infoTitle.setText("What's your Height & Weight?");
                    } else {
                        heightEditText.setVisibility(View.GONE);
                        weightEditText.setVisibility(View.GONE);
                        heightTitle.setVisibility(View.GONE);
                        weightTitle.setVisibility(View.GONE);
                    }
                if (isActivityActivated) {
                        activity.setVisibility(View.VISIBLE);
                        infoTitle.setText("How is your Activity?");
                    } else {
                        activity.setVisibility(View.GONE);
                    }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}