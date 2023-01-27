package com.soha.foodplanner.ui.local_details.presenter;

import android.view.View;

import com.soha.foodplanner.data.dto.meal.MealsItem;
import com.soha.foodplanner.data.local.Meal;

public interface LocalDetailsListener {
    void getLocalMealDetail();
    void setLocalValues(Meal meal, View view);
}
