package com.soha.foodplanner.ui.meal_details.presenter;

import android.view.View;

import com.soha.foodplanner.data.local.entities.Meal;
import com.soha.foodplanner.data.local.model.CompleteIngredient;
import com.soha.foodplanner.data.local.model.CompleteMeal;

import java.util.List;

public interface MealDetailsListener {
    void getMealDetail();
    void setValues(CompleteMeal mealsItem);
    void setIngredients(List<CompleteIngredient> mealsItem, View view);

}
