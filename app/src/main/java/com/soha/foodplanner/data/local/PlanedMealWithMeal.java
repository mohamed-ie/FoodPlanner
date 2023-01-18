package com.soha.foodplanner.data.local;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PlanedMealWithMeal {
    @Embedded
    public PlannedMeals plannedMeals;
    @Relation(parentColumn = "meal_id",entityColumn = "id")
    public List<Meal> meals;

}
