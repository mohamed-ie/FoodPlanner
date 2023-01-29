package com.soha.foodplanner.data.local.entities;


import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PlannedMealWithMealAndIngredients {
    @Embedded
    private final PlannedMeals plannedMeals;
    @Relation(entity = Meal.class,
            parentColumn = "meal_id",
            entityColumn = "id")
    private final List<IngredientWithMeal> meals;

    public PlannedMealWithMealAndIngredients(PlannedMeals plannedMeals, List<IngredientWithMeal> meals) {
        this.plannedMeals = plannedMeals;
        this.meals = meals;
    }

    public PlannedMeals getPlannedMeals() {
        return plannedMeals;
    }

    public List<IngredientWithMeal> getMeals() {
        return meals;
    }
}
