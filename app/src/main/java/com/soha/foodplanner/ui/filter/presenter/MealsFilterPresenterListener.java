package com.soha.foodplanner.ui.filter.presenter;

import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.ui.common.presenter.PresenterListener;

import java.util.List;

public interface MealsFilterPresenterListener extends PresenterListener {
    void onGetMealsByCategorySuccess(List<MinMeal> minMeals);

    void onGetMealsByCategoryError(String message);

    void onGetMealsByCategoryLoading();
}
