package com.soha.foodplanner.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite_meals")
public class FavouriteMeals {
    @PrimaryKey
    @ColumnInfo(name = "meal_id")
    private long mealId;

    public FavouriteMeals(long mealId) {

        this.mealId = mealId;
    }

    
    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }
}
