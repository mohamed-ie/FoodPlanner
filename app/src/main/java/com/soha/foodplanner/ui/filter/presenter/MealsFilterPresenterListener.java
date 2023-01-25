package com.soha.foodplanner.ui.filter.presenter;

import com.soha.foodplanner.data.local.model.MinMeal;

import java.util.List;

public interface MealsFilterPresenterListener {
    void onGetMealsByCategorySuccess(List<MinMeal> minMeals);

    void onGetMealsByCategoryError(String message);

    void onGetMealsByCategoryLoading();
}
