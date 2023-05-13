package com.example.nutrilab.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.nutrilab.R;

public class HomeFragment extends Fragment {

    private EditText ageEditText;
    private TextView infosTitle;

    private LinearLayout genderChoice;
    private ImageView maleIcon;
    private ImageView femaleIcon;

    private EditText heightEditText;
    private EditText weightEditText;
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
        infosTitle = view.findViewById(R.id.infosTitle);

        genderChoice = view.findViewById(R.id.genderChoice);
        maleIcon = view.findViewById(R.id.maleIcon);
        femaleIcon = view.findViewById(R.id.femaleIcon);

        heightEditText = view.findViewById(R.id.heightEditText);
        weightEditText = view.findViewById(R.id.weightEditText);

        nextButton = view.findViewById(R.id.nextButton);
        backButton = view.findViewById(R.id.backButton);

//        maleIcon.setOnHoverListener(new View.OnHoverListener() {
//            @Override
//            public boolean onHover(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        // Do something when the image is touched
//                        maleIcon.setBackgroundResource(R.drawable.male_background);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        // Do something when the touch is released
//                        maleIcon.setBackgroundResource(0);
//                        break;
//                }
//                return true;
//            }
//        });

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

                    if (age >= 12){
                        infosTitle.setText("What's your gender?");

                        // Hide the age field and show the gender field
                        ageEditText.setVisibility(View.GONE);
                        genderChoice.setVisibility(View.VISIBLE);
                        backButton.setVisibility(View.VISIBLE);
                    }
                }

                if (genderChoice.getVisibility() == View.VISIBLE) {
                    if (gender == "male" || gender == "female"){
                        infosTitle.setText("What's your Height and Weight?");

                        genderChoice.setVisibility(View.GONE);
                        heightEditText.setVisibility(View.VISIBLE);
                        weightEditText.setVisibility(View.VISIBLE);
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
                    infosTitle.setText("How old are you?");
                }

                if (heightEditText.getVisibility() == View.VISIBLE) {
                    // Show the age field and hide the gender field
                    heightEditText.setVisibility(View.GONE);
                    weightEditText.setVisibility(View.GONE);

                    genderChoice.setVisibility(View.VISIBLE);
                    infosTitle.setText("What's your gender?");
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