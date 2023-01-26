package com.soha.foodplanner.data.local;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

public class FavouriteMealsWithMeal {
    @Embedded
    private FavouriteMeals favouriteMeals;
    @Relation(parentColumn = "meal_id",entityColumn = "id")
    private Meal meal;

    public FavouriteMeals getFavouriteMeals() {
        return favouriteMeals;
    }

    public void setFavouriteMeals(FavouriteMeals favouriteMeals) {
        this.favouriteMeals = favouriteMeals;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}
