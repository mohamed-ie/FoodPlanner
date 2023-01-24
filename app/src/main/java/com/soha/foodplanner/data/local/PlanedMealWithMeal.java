package com.soha.foodplanner.data.local;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PlanedMealWithMeal {
    @Embedded
    public PlannedMeals plannedMeals;
    @Relation(parentColumn = "meal_id",entityColumn = "id")
    public List<Meal> meals;

    public PlannedMeals getPlannedMeals() {
        return plannedMeals;
    }

    public void setPlannedMeals(PlannedMeals plannedMeals) {
        this.plannedMeals = plannedMeals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
