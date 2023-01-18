package com.soha.foodplanner.data.local;

import androidx.room.Embedded;
import androidx.room.Relation;

public class FavouriteMealsWithMeal {
    @Embedded
    public FavouriteMeals favouriteMeals;
    @Relation(parentColumn = "meal_id",entityColumn = "id")
    public Meal meal;
}
