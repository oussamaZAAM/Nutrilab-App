package com.example.nutrilab.ui.home.infos;

import static java.lang.Double.parseDouble;

import java.util.HashMap;
import java.util.Map;

public class NutritionCalculator {
    public static Map<String, Double> calculateNutrients(int age, String gender, float height, float weight, String activity, String plan) {
        // Calculate BMR : Harris-Benedict Calculator
        float BMR = 0;
        if (gender.equals("male")) {
            BMR = 66.5f + 13.75f * weight + 5.003f * height - 6.75f * age;
        } else if (gender.equals("female")) {
            BMR = 655.1f + 9.563f * weight + 1.85f * height - 4.676f * age;
        }

        // Calculate Calories
        float kCalories = 0;
        switch (activity) {
            case "sedentary":
                kCalories = 1.2f * BMR;
                break;
            case "lightly_active":
                kCalories = 1.375f * BMR;
                break;
            case "moderately_active":
                kCalories = 1.55f * BMR;
                break;
            case "very_active":
                kCalories = 1.725f * BMR;
                break;
            case "super_active":
                kCalories = 1.9f * BMR;
                break;
        }

        if (plan.equals("lose_weight")) {
            kCalories -= 500;
        } else if (plan.equals("build_muscle")) {
            kCalories += 500;
        }

        // Calculate Proteins
        float proteins = 0;
        switch (plan) {
            case "maintain":
                switch (activity) {
                    case "sedentary":
                    case "lightly_active":
                        proteins = 0.8f * weight;
                        break;
                    case "moderately_active":
                        proteins = 0.9f * weight;
                        break;
                    case "very_active":
                    case "super_active":
                        proteins = 1.0f * weight;
                        break;
                }
                break;
            case "lose_weight":
                switch (activity) {
                    case "sedentary":
                        proteins = 1.2f * weight;
                        break;
                    case "lightly_active":
                        proteins = 1.3f * weight;
                        break;
                    case "moderately_active":
                        proteins = 1.4f * weight;
                        break;
                    case "very_active":
                        proteins = 1.5f * weight;
                        break;
                    case "super_active":
                        proteins = 1.6f * weight;
                        break;
                }
                break;
            case "build_muscle":
                switch (activity) {
                    case "sedentary":
                        proteins = 1.6f * weight;
                        break;
                    case "lightly_active":
                        proteins = 1.7f * weight;
                        break;
                    case "moderately_active":
                        proteins = 1.8f * weight;
                        break;
                    case "very_active":
                        proteins = 1.9f * weight;
                        break;
                    case "super_active":
                        proteins = 2.0f * weight;
                        break;
                }
                break;
        }

        // Calculate Fats
        float fats = 0;
        switch (plan) {
            case "maintain":
                fats = (kCalories * 0.275f) / 9;
                break;
            case "lose_weight":
                fats = 0.75f * weight;
                break;
            case "build_muscle":
                fats = 1.0f * weight;
                break;
        }
        // Calculate Carbs
        float carbs = 0;
        switch (plan) {
            case "lose_weight":
                carbs = (kCalories * 0.45f) / 4;
                break;
            case "maintain":
                carbs = (kCalories * 0.55f) / 4;
                break;
            case "build_muscle":
                carbs = (kCalories * 0.65f) / 4;
                break;
        }

        // Calculate Iron
        float iron = 0;
        if (gender.equals("female")) {
            if (age <= 50) {
                iron = 0.018f;
            } else {
                iron = 0.008f;
            }
        } else {
            iron = 0.008f;
        }

        // Calculate Fiber
        float fiber = (kCalories / 1000) * 14;

        // Calculate Sugar
        float sugar;
        if (gender.equals("female")) {
            sugar = 24;
        } else {
            sugar = 36;
        }

        // Calculate Salt
        float salt = 6;

        // Create a map to store the nutrient values
        Map<String, Double> nutrients = new HashMap<>();
        nutrients.put("kCalories", parseDouble(String.valueOf(Math.round(kCalories))));
        nutrients.put("proteins", parseDouble(String.valueOf(Math.round(proteins))));
        nutrients.put("fats", parseDouble(String.valueOf(Math.round(fats))));
        nutrients.put("carbs", parseDouble(String.valueOf(Math.round(carbs))));
        nutrients.put("iron", parseDouble(String.valueOf(Math.round(iron))));
        nutrients.put("fiber", parseDouble(String.valueOf(Math.round(fiber))));
        nutrients.put("sugar", parseDouble(String.valueOf(Math.round(sugar))));
        nutrients.put("salt", parseDouble(String.valueOf(Math.round(salt))));

        return nutrients;
    }
}