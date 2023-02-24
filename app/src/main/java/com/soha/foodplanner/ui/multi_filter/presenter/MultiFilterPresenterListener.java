package com.soha.foodplanner.ui.multi_filter.presenter;

import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.ui.common.presenter.PresenterListener;

public interface MultiFilterPresenterListener extends PresenterListener {
    void onNextMeal(CompleteMeal completeMeal, int progress);

    void onLoadAllMealsComplete();

    void onLoadAllMealsError(String message, int progress);

    void onSearchNextMeal(CompleteMeal completeMeal);

    void clearList();
}
