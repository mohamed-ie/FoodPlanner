package com.soha.foodplanner.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite_meals")
public class FavouriteMeals {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "meal_id")
    private long mealId;

    public FavouriteMeals(long id, long mealId) {
        this.id = id;
        this.mealId = mealId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }
}
