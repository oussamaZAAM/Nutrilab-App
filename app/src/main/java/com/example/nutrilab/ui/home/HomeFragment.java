package com.example.nutrilab.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.nutrilab.R;

import java.math.BigDecimal;

public class HomeFragment extends Fragment {

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

    private int age;
    private String gender;
    private float height;
    private float weight;

    @SuppressLint({"CutPasteId", "ClickableViewAccessibility"})
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ageEditText = view.findViewById(R.id.ageEditText);
        infoTitle = view.findViewById(R.id.infosTitle);

        genderChoice = view.findViewById(R.id.genderChoice);
        maleIcon = view.findViewById(R.id.maleIcon);
        femaleIcon = view.findViewById(R.id.femaleIcon);

        heightEditText = view.findViewById(R.id.heightEditText);
        weightEditText = view.findViewById(R.id.weightEditText);
        heightTitle = view.findViewById(R.id.heightTitle);
        weightTitle = view.findViewById(R.id.weightTitle);

        nextButton = view.findViewById(R.id.nextButton);
        backButton = view.findViewById(R.id.backButton);

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

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ageEditText.getVisibility() == View.VISIBLE) {
                    // Get the value of the age field and store it in a variable
                    age = Integer.parseInt(ageEditText.getText().toString());

                    if (age >= 12 && age <= 120){
                        infoTitle.setText("What's your gender?");

                        // Hide the age field and show the gender field
                        ageEditText.setVisibility(View.GONE);
                        genderChoice.setVisibility(View.VISIBLE);
                        backButton.setVisibility(View.VISIBLE);
                    }
                }

                if (genderChoice.getVisibility() == View.VISIBLE) {
                    if (gender == "male" || gender == "female"){
                        infoTitle.setText("What's your Height & Weight?");

                        genderChoice.setVisibility(View.GONE);
                        heightEditText.setVisibility(View.VISIBLE);
                        weightEditText.setVisibility(View.VISIBLE);
                        heightTitle.setVisibility(View.VISIBLE);
                        weightTitle.setVisibility(View.VISIBLE);
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

                    if (height >= 80 && weight >= 20){
                        heightEditText.setVisibility(View.GONE);
                        weightEditText.setVisibility(View.GONE);
                        heightTitle.setVisibility(View.GONE);
                        weightTitle.setVisibility(View.GONE);

                        infoTitle.setText("How is your Activity?");
                    }
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genderChoice.getVisibility() == View.VISIBLE) {
                    // Show the age field and hide the gender field
                    ageEditText.setVisibility(View.VISIBLE);
                    genderChoice.setVisibility(View.GONE);
                    backButton.setVisibility(View.INVISIBLE);
                    infoTitle.setText("How old are you?");
                }

                if (heightEditText.getVisibility() == View.VISIBLE) {
                    // Show the age field and hide the gender field
                    heightEditText.setVisibility(View.GONE);
                    weightEditText.setVisibility(View.GONE);

                    genderChoice.setVisibility(View.VISIBLE);
                    infoTitle.setText("What's your gender?");
                }

                if (heightEditText.getVisibility() == View.VISIBLE && weightEditText.getVisibility() == View.VISIBLE) {
                    // Show the age field and hide the gender field
                    heightEditText.setVisibility(View.GONE);
                    weightEditText.setVisibility(View.GONE);
                    heightTitle.setVisibility(View.GONE);
                    weightTitle.setVisibility(View.GONE);

                    genderChoice.setVisibility(View.VISIBLE);
                    infoTitle.setText("What's your gender?");
                }


            }
        });

        System.out.println("Age: "+age+", Gender: "+gender);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}