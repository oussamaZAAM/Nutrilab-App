package com.example.nutrilab.ui.infos;

import static com.example.nutrilab.ui.infos.NutritionCalculator.calculateNutrients;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.nutrilab.R;
import com.example.nutrilab.ui.general.SharedPrefsHelper;

import java.io.Serializable;
import java.util.Map;

public class InfosFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private static final String PREFS_NAME = "MyPrefs";
    private static final String NUTRIENTS = "nutrients";

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

    private LinearLayout activityMenu;
    private Spinner activitySpinner;

    private LinearLayout planMenu;
    private Spinner planSpinner;

    private TableLayout recapTable;
    private TextView ageRecap;
    private TextView genderRecap;
    private TextView heightRecap;
    private TextView weightRecap;
    private TextView activityRecap;
    private TextView planRecap;

    private Button recapShortcut;

    private int age;
    private String gender;
    private float height;
    private float weight;
    private String activity;
    private String plan;

    public boolean isAgeActivated;
    public boolean isGenderActivated;
    public boolean isHeightActivated;
    public boolean isActivityActivated;
    public boolean isPlanActivated;
    public boolean isRecapActivated;
    public boolean isRecapShortcutActivated;

    @SuppressLint({"CutPasteId", "ClickableViewAccessibility"})
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_infos, container, false);

        // Populate data inside Activity dropdown menu
        ArrayAdapter<CharSequence> activityAdapter = ArrayAdapter.createFromResource(getContext(), R.array.activities, android.R.layout.simple_spinner_item);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Populate data inside Activity dropdown menu
        ArrayAdapter<CharSequence> planAdapter = ArrayAdapter.createFromResource(getContext(), R.array.plans, android.R.layout.simple_spinner_item);
        planAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ageEditText = view.findViewById(R.id.ageEditText);
        infoTitle = view.findViewById(R.id.infoTitle);

        genderChoice = view.findViewById(R.id.genderChoice);
        maleIcon = view.findViewById(R.id.maleIcon);
        femaleIcon = view.findViewById(R.id.femaleIcon);

        heightEditText = view.findViewById(R.id.heightEditText);
        weightEditText = view.findViewById(R.id.weightEditText);
        heightTitle = view.findViewById(R.id.heightTitle);
        weightTitle = view.findViewById(R.id.weightTitle);

        activitySpinner = view.findViewById(R.id.activitySpinner);
        activitySpinner.setAdapter(activityAdapter);
        activityMenu = view.findViewById(R.id.activityMenu);

        planSpinner = view.findViewById(R.id.planSpinner);
        planSpinner.setAdapter(planAdapter);
        planMenu = view.findViewById(R.id.planMenu);

        recapTable = view.findViewById(R.id.recapTable);
        ageRecap = view.findViewById(R.id.ageRecap);
        genderRecap = view.findViewById(R.id.genderRecap);
        heightRecap = view.findViewById(R.id.heightRecap);
        weightRecap = view.findViewById(R.id.weightRecap);
        activityRecap = view.findViewById(R.id.activityRecap);
        planRecap = view.findViewById(R.id.planRecap);
        Button ageEditButton = view.findViewById(R.id.ageEditButton);
        Button genderEditButton = view.findViewById(R.id.genderEditButton);
        Button heightEditButton = view.findViewById(R.id.heightEditButton);
        Button weightEditButton = view.findViewById(R.id.weightEditButton);
        Button activityEditButton = view.findViewById(R.id.activityEditButton);
        Button planEditButton = view.findViewById(R.id.planEditButton);

        recapShortcut = view.findViewById(R.id.recapShortcut);

        nextButton = view.findViewById(R.id.nextButton);
        backButton = view.findViewById(R.id.backButton);


        // Get the Age value from shared preferences
        try {
            age = SharedPrefsHelper.loadInteger(requireContext(), PREFS_NAME, "AGE");
            ageEditText.setText(String.valueOf(age));
        } catch (Exception e) {
        }

        // Get the Gender value from shared preferences
        try {
            gender = SharedPrefsHelper.loadString(requireContext(), PREFS_NAME, "GENDER");
            if (gender.equals("male")){
                maleIcon.setOnClickListener(v -> {
                    maleIcon.setBackgroundResource(R.drawable.male_background);
                    femaleIcon.setBackgroundResource(0);
                });
            }
            if (gender.equals("female")) {
                femaleIcon.setOnClickListener(v -> {
                    maleIcon.setBackgroundResource(0);
                    femaleIcon.setBackgroundResource(R.drawable.female_background);
                });
            }
        } catch (Exception e) {
        }

        // Get the Height value from shared preferences
        try {
            height = SharedPrefsHelper.loadFloat(requireContext(), PREFS_NAME, "HEIGHT");
            weight = SharedPrefsHelper.loadFloat(requireContext(), PREFS_NAME, "WEIGHT");
            heightEditText.setText(String.valueOf(height));
            weightEditText.setText(String.valueOf(weight));
        } catch (Exception e) {
        }

        // Get the Activity value from shared preferences
        try {
            activity = SharedPrefsHelper.loadString(requireContext(), PREFS_NAME, "ACTIVITY");
            activitySpinner.setSelection(3);
        } catch (Exception e) {
        }

        // Get the Activity value from shared preferences
        try {
            plan = SharedPrefsHelper.loadString(requireContext(), PREFS_NAME, "PLAN");
            planSpinner.setSelection(1);
        } catch (Exception e) {
        }

        if ((age >= 12 && age <= 120) && (gender.equals("male") || gender.equals("female")) && (height >= 80 && height <= 300) && (weight >= 20 && weight <= 200) && (activity.equals("sedentary") || activity.equals("lightly_active") || activity.equals("moderately_active") || activity.equals("very_active") || activity.equals("super_active")) && (plan.equals("maintain") || plan.equals("lose_weight") || plan.equals("build_muscle"))) {
            recapShortcut.setVisibility(View.VISIBLE);
        } else {
            recapShortcut.setVisibility(View.GONE);
        }


        isAgeActivated = true;
        isGenderActivated = isHeightActivated = isActivityActivated = isPlanActivated = isRecapActivated = false;

        if ((age >= 12 && age <= 120) && (gender.equals("male") || gender.equals("female")) && (height >= 80 && height <= 300) && (weight >= 20 && weight <= 200) && (activity.equals("sedentary") || activity.equals("lightly_active") || activity.equals("moderately_active") || activity.equals("very_active") || activity.equals("super_active")) && (plan.equals("maintain") || plan.equals("lose_weight") || plan.equals("build_muscle"))) {
            isRecapShortcutActivated = true;
            performScreensLogic(view);
        } else {
            isRecapShortcutActivated = false;
        }
        // Event Listeners

        ageEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                ageEditText.setBackgroundResource(R.drawable.edittext_border);
            } else {
                ageEditText.setBackgroundResource(R.drawable.edittext_focused);
                if (age == 0) {
                    ageEditText.setText("");
                }
            }
        });

        heightEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                heightEditText.setBackgroundResource(R.drawable.edittext_border);
            } else {
                heightEditText.setBackgroundResource(R.drawable.edittext_focused);
                if (height == 0.0f) {
                    heightEditText.setText("");
                }
            }
        });

        weightEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                weightEditText.setBackgroundResource(R.drawable.edittext_border);
            } else {
                weightEditText.setBackgroundResource(R.drawable.edittext_focused);
                if (weight == 0.0f) {
                    weightEditText.setText("");
                }
            }
        });

        ageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called before the text is changed.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    age = Integer.parseInt(s.toString());
                } catch (NumberFormatException e) {
                    age = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        heightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called before the text is changed.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    height = Float.parseFloat(s.toString());
                } catch (NumberFormatException e) {
                    height = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        weightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called before the text is changed.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    weight = Float.parseFloat(s.toString());
                } catch (NumberFormatException e) {
                    weight = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        maleIcon.setOnClickListener(v -> {
            gender = "male";
            maleIcon.setBackgroundResource(R.drawable.male_background);
            femaleIcon.setBackgroundResource(0);
        });

        femaleIcon.setOnClickListener(v -> {
            gender = "female";
            maleIcon.setBackgroundResource(0);
            femaleIcon.setBackgroundResource(R.drawable.female_background);
        });

        activityMenu.setOnClickListener(v -> activitySpinner.performClick());

        activitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = parent.getItemAtPosition(position).toString();
                switch (selectedValue) {
                    case "Sedentary":
                        activity = "sedentary";
                        break;
                    case "Lightly Active":
                        activity = "lightly_active";
                        break;
                    case "Moderately Active":
                        activity = "moderately_active";
                        break;
                    case "Very Active":
                        activity = "very_active";
                        break;
                    case "Super Active":
                        activity = "super_active";
                        break;
                    default:
                        activity = "sedentary";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        planMenu.setOnClickListener(v -> planSpinner.performClick());

        planSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = parent.getItemAtPosition(position).toString();
                switch (selectedValue) {
                    case "Maintain":
                        plan = "maintain";
                        break;
                    case "Lose Weight":
                        plan = "lose_weight";
                        break;
                    case "Build Muscle":
                        plan = "build_muscle";
                        break;
                    default:
                        plan = "maintain";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        ageEditButton.setOnClickListener(v -> {
            isAgeActivated = true;
            isRecapActivated = false;
            performScreensLogic(view);
        });
        genderEditButton.setOnClickListener(v -> {
            isGenderActivated = true;
            isRecapActivated = false;
            performScreensLogic(view);
        });
        heightEditButton.setOnClickListener(v -> {
            isHeightActivated = true;
            isRecapActivated = false;
            performScreensLogic(view);
        });
        weightEditButton.setOnClickListener(v -> {
            isHeightActivated = true;
            isRecapActivated = false;
            performScreensLogic(view);
        });
        activityEditButton.setOnClickListener(v -> {
            isActivityActivated = true;
            isRecapActivated = false;
            performScreensLogic(view);
        });
        planEditButton.setOnClickListener(v -> {
            isPlanActivated = true;
            isRecapActivated = false;
            performScreensLogic(view);
        });
        recapShortcut.setOnClickListener(v -> {
            isAgeActivated = false;
            isGenderActivated = false;
            isHeightActivated = false;
            isActivityActivated = false;
            isPlanActivated = false;
            isRecapActivated = true;
            performScreensLogic(view);
        });

        nextButton.setOnClickListener(v -> {
            if (ageEditText.getVisibility() == View.VISIBLE) {
                if (age >= 12 && age <= 120) {
                    // Hide the age field and show the gender field
                    isAgeActivated = false;
                    isGenderActivated = true;
                }
            }
            if (genderChoice.getVisibility() == View.VISIBLE) {
                if (gender == "male" || gender == "female") {
                    isGenderActivated = false;
                    isHeightActivated = true;
                }
            }
            if (heightEditText.getVisibility() == View.VISIBLE && weightEditText.getVisibility() == View.VISIBLE) {
                if ((height >= 80 && height <= 300) && (weight >= 20 && weight <= 200)) {
                    isHeightActivated = false;
                    isActivityActivated = true;
                }
            }
            if (activityMenu.getVisibility() == View.VISIBLE) {
                if (activity.equals("sedentary") || activity.equals("lightly_active") || activity.equals("moderately_active") || activity.equals("very_active") || activity.equals("super_active")) {
                    isActivityActivated = false;
                    isPlanActivated = true;
                }
            }
            if (planMenu.getVisibility() == View.VISIBLE) {
                if (plan.equals("maintain") || plan.equals("lose_weight") || plan.equals("build_muscle")) {
                    isPlanActivated = false;
                    isRecapActivated = true;
                    isRecapShortcutActivated = true;
                }
            }
            if (recapTable.getVisibility() == View.VISIBLE) {
                Map<String, Double> nutrients = calculateNutrients(age, gender, height, weight, activity, plan);

                // Store data in Shared Preferences Store
                SharedPrefsHelper.saveMap(requireContext(), PREFS_NAME, NUTRIENTS, nutrients);

                // Check if data is legit
                if ((age >= 12 && age <= 120) && (gender.equals("male") || gender.equals("female")) && (height >= 80 && height <= 300) && (weight >= 20 && weight <= 200) && (activity.equals("sedentary") || activity.equals("lightly_active") || activity.equals("moderately_active") || activity.equals("very_active") || activity.equals("super_active")) && (plan.equals("maintain") || plan.equals("lose_weight") || plan.equals("build_muscle"))) {
                    // Pass Data and Navigate
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("nutrients", (Serializable) nutrients);
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.navigation_dashboard, bundle);
                }
            }

            // Apply changes to View
            performScreensLogic(view);
        });

        backButton.setOnClickListener(v -> {
            if (genderChoice.getVisibility() == View.VISIBLE) {
                // Show the age field and hide the gender field
                isGenderActivated = false;
                isAgeActivated = true;
            }
            if (heightEditText.getVisibility() == View.VISIBLE && weightEditText.getVisibility() == View.VISIBLE) {
                // Show the gender field and hide the h/weight field
                isHeightActivated = false;
                isGenderActivated = true;
            }
            if (activityMenu.getVisibility() == View.VISIBLE) {
                isActivityActivated = false;
                isHeightActivated = true;
            }
            if (planMenu.getVisibility() == View.VISIBLE) {
                isPlanActivated = false;
                isActivityActivated = true;
            }
            if (recapTable.getVisibility() == View.VISIBLE) {
                isRecapActivated = false;
                isPlanActivated = true;
            }

            // Apply changes to View
            performScreensLogic(view);
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPrefsHelper.saveInteger(requireContext(), PREFS_NAME, "AGE", age);
        SharedPrefsHelper.saveString(requireContext(), PREFS_NAME, "GENDER", gender);
        SharedPrefsHelper.saveFloat(requireContext(), PREFS_NAME, "HEIGHT", height);
        SharedPrefsHelper.saveFloat(requireContext(), PREFS_NAME, "WEIGHT", weight);
        SharedPrefsHelper.saveString(requireContext(), PREFS_NAME, "ACTIVITY", activity);
        SharedPrefsHelper.saveString(requireContext(), PREFS_NAME, "PLAN", plan);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the Age value from shared preferences
        try {
            age = SharedPrefsHelper.loadInteger(requireContext(), PREFS_NAME, "AGE");
            ageEditText.setText(String.valueOf(age));
        } catch (Exception e) {
        }

        // Get the Gender value from shared preferences
        try {
            gender = SharedPrefsHelper.loadString(requireContext(), PREFS_NAME, "GENDER");
            if (gender.equals("female")) {
                maleIcon.setBackgroundResource(0);
                femaleIcon.setBackgroundResource(R.drawable.female_background);
            } else {
                maleIcon.setBackgroundResource(R.drawable.male_background);
                femaleIcon.setBackgroundResource(0);
            }
        } catch (Exception e) {
        }

        // Get the Height value from shared preferences
        try {
            height = SharedPrefsHelper.loadFloat(requireContext(), PREFS_NAME, "HEIGHT");
            weight = SharedPrefsHelper.loadFloat(requireContext(), PREFS_NAME, "WEIGHT");
            heightEditText.setText(String.valueOf(height));
            weightEditText.setText(String.valueOf(weight));
        } catch (Exception e) {
        }

        // Get the Activity value from shared preferences
        try {
            activity = SharedPrefsHelper.loadString(requireContext(), PREFS_NAME, "ACTIVITY");
            activitySpinner.setSelection(3);
        } catch (Exception e) {
        }

        // Get the Activity value from shared preferences
        try {
            plan = SharedPrefsHelper.loadString(requireContext(), PREFS_NAME, "PLAN");
            planSpinner.setSelection(1);
        } catch (Exception e) {
        }

        if ((age >= 12 && age <= 120) && (gender.equals("male") || gender.equals("female")) && (height >= 80 && height <= 300) && (weight >= 20 && weight <= 200) && (activity.equals("sedentary") || activity.equals("lightly_active") || activity.equals("moderately_active") || activity.equals("very_active") || activity.equals("super_active")) && (plan.equals("maintain") || plan.equals("lose_weight") || plan.equals("build_muscle"))) {
            recapShortcut.setVisibility(View.VISIBLE);
        } else {
            recapShortcut.setVisibility(View.GONE);
        }
    }


    private void performScreensLogic(View rootView) {
        // Set Layout params for layout elements
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) infoTitle.getLayoutParams();
        float scale = getResources().getDisplayMetrics().density;

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
            activityMenu.setVisibility(View.VISIBLE);
            infoTitle.setText("How is your Activity?");
        } else {
            activityMenu.setVisibility(View.GONE);
        }
        if (isPlanActivated) {
            planMenu.setVisibility(View.VISIBLE);
            infoTitle.setText("What is your Plan?");
        } else {
            planMenu.setVisibility(View.GONE);
        }
        if (isRecapActivated) {
            recapTable.setVisibility(View.VISIBLE);
            recapShortcut.setVisibility(View.GONE);

            ageRecap.setText(String.valueOf(age));
            genderRecap.setText(gender);
            heightRecap.setText(String.valueOf(height)+" cm");
            weightRecap.setText(String.valueOf(weight)+" kg");
            switch (activity) {
                case "sedentary":
                    activityRecap.setText("Sedentary");
                    break;
                case "lightly_active":
                    activityRecap.setText("Lightly Active");
                    break;
                case "moderately_active":
                    activityRecap.setText("Moderately Active");
                    break;
                case "very_active":
                    activityRecap.setText("Very Active");
                    break;
                case "super_active":
                    activityRecap.setText("Super Active");
                    break;
                default:
                    activityRecap.setText("Sedentary");
                    break;
            }
            switch (plan) {
                case "maintain":
                    planRecap.setText("Maintain");
                    break;
                case "lose_weight":
                    planRecap.setText("Lose Weight");
                    break;
                case "build_muscle":
                    planRecap.setText("Build Muscle");
                    break;
                default:
                    planRecap.setText("Maintain");
                    break;
            }

            infoTitle.setText("Recap");

            layoutParams.setMargins(0, (int) (8 * scale + 0.5f), 0, (int) (8 * scale + 0.5f));
            infoTitle.setLayoutParams(layoutParams);
            nextButton.setText("Apply");
        } else {
            recapTable.setVisibility(View.GONE);
            layoutParams.setMargins(0, (int) (158 * scale + 0.5f), 0, (int) (8 * scale + 0.5f));
            infoTitle.setLayoutParams(layoutParams);
            nextButton.setText("Next");
        }
        if (isRecapShortcutActivated && !isRecapActivated) {
            recapShortcut.setVisibility(View.VISIBLE);
        }
    }
}