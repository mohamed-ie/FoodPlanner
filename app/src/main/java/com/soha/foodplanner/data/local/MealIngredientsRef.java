package com.soha.foodplanner.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(primaryKeys = {"id","name"})
public class MealIngredientsRef {
    @ColumnInfo(name = "id")
    private final long mealId;
    @ColumnInfo(name = "name")
    @androidx.annotation.NonNull
    private final String ingredientId;
    private final String amount;

    public MealIngredientsRef(long mealId, @androidx.annotation.NonNull String ingredientId, String amount) {
        this.mealId = mealId;
        this.ingredientId = ingredientId;
        this.amount = amount;
    }

    public long getMealId() {
        return mealId;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public String getAmount() {
        return amount;
    }
}
