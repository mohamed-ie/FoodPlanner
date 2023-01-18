package com.soha.foodplanner.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"id","name"})
public class MealIngredientsRef {
    @ColumnInfo(name = "id")
    public long mealId;
    @ColumnInfo(name = "name")
    public long ingredientId;
    public String amount;
}
