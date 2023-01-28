package com.soha.foodplanner.ui.meal_details.presenter;

import android.view.View;

import com.soha.foodplanner.data.dto.meal.MealsItem;
import com.soha.foodplanner.data.local.entities.Meal;
import com.soha.foodplanner.data.local.model.CompleteIngredient;
import com.soha.foodplanner.data.local.model.MinIngredient;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface MealDetailsListener {
    void getMealDetail();
    void setValues(Meal mealsItem, View view);
    void setIngredients(List<CompleteIngredient> mealsItem, View view);

}
