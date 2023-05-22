package com.example.nutrilab.ui.infos;

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
        if (activity.equals("sedentary")) {
            kCalories = 1.2f * BMR;
        } else if (activity.equals("lightly_active")) {
            kCalories = 1.375f * BMR;
        } else if (activity.equals("moderately_active")) {
            kCalories = 1.55f * BMR;
        } else if (activity.equals("very_active")) {
            kCalories = 1.725f * BMR;
        } else if (activity.equals("super_active")) {
            kCalories = 1.9f * BMR;
        }

        if (plan.equals("lose_weight")) {
            kCalories -= 500;
        } else if (plan.equals("build_muscle")) {
            kCalories += 500;
        }

        // Calculate Proteins
        float proteins = 0;
        if (plan.equals("maintain")) {
            if (activity.equals("sedentary") || activity.equals("lightly_active")) {
                proteins = 0.8f * weight;
            } else if (activity.equals("moderately_active")) {
                proteins = 0.9f * weight;
            } else if (activity.equals("very_active") || activity.equals("super_active")) {
                proteins = 1.0f * weight;
            }
        } else if (plan.equals("lose_weight")) {
            if (activity.equals("sedentary")) {
                proteins = 1.2f * weight;
            } else if (activity.equals("lightly_active")) {
                proteins = 1.3f * weight;
            } else if (activity.equals("moderately_active")) {
                proteins = 1.4f * weight;
            } else if (activity.equals("very_active")) {
                proteins = 1.5f * weight;
            } else if (activity.equals("super_active")) {
                proteins = 1.6f * weight;
            }
        } else if (plan.equals("build_muscle")) {
            if (activity.equals("sedentary")) {
                proteins = 1.6f * weight;
            } else if (activity.equals("lightly_active")) {
                proteins = 1.7f * weight;
            } else if (activity.equals("moderately_active")) {
                proteins = 1.8f * weight;
            } else if (activity.equals("very_active")) {
                proteins = 1.9f * weight;
            } else if (activity.equals("super_active")) {
                proteins = 2.0f * weight;
            }
        }

        // Calculate Fats
        float fats = 0;
        if (plan.equals("maintain")) {
            fats = (kCalories * 0.275f) / 9;
        } else if (
                plan.equals("lose_weight")) {
            fats = 0.75f * weight;
        } else if (plan.equals("build_muscle")) {
            fats = 1.0f * weight;
        }
        // Calculate Carbs
        float carbs = 0;
        if (plan.equals("lose_weight")) {
            carbs = (kCalories * 0.45f) / 4;
        } else if (plan.equals("maintain")) {
            carbs = (kCalories * 0.55f) / 4;
        } else if (plan.equals("build_muscle")) {
            carbs = (kCalories * 0.65f) / 4;
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
        nutrients.put("kCalories", (double) Math.round(kCalories));
        nutrients.put("proteins", (double) Math.round(proteins));
        nutrients.put("fats", (double) Math.round(fats));
        nutrients.put("carbs", (double) Math.round(carbs));
        nutrients.put("iron", (double) Math.round(iron));
        nutrients.put("fiber", (double) Math.round(fiber));
        nutrients.put("sugar", (double) Math.round(sugar));
        nutrients.put("salt", (double) Math.round(salt));

        return nutrients;
    }
}