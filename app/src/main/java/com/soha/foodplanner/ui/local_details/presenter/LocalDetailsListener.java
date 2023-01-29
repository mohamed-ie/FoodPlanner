package com.soha.foodplanner.ui.local_details.presenter;

import com.soha.foodplanner.data.local.entities.IngredientWithMeal;

import io.reactivex.rxjava3.annotations.NonNull;

public interface LocalDetailsListener {
    void getLocalMealDetail();
    void setLocalValues(@NonNull IngredientWithMeal meal);
}
