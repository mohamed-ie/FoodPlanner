package com.soha.foodplanner.ui.meal_details.presenter;

import android.view.View;

import com.soha.foodplanner.data.dto.meal.MealsItem;

public interface MealDetailsListener {
    void getMealDetail();
    void setValues(MealsItem mealsItem, View view);
}
