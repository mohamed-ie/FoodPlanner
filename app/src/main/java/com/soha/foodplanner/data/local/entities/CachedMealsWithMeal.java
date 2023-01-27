package com.soha.foodplanner.data.local.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CachedMealsWithMeal {
    @Embedded
    public CachedMeals cachedMeals;
    @Relation(parentColumn = "meal_id",entityColumn = "id")
    public List<Meal> meals;
}
