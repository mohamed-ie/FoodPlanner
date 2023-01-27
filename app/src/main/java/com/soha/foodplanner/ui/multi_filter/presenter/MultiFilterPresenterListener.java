package com.soha.foodplanner.ui.multi_filter.presenter;

import com.soha.foodplanner.data.local.model.CompleteMeal;

public interface MultiFilterPresenterListener {
    void onNextMeal(CompleteMeal completeMeal, int progress);

    void onLoadAllMealsComplete();

    void onLoadAllMealsError(String message, int progress);
}
