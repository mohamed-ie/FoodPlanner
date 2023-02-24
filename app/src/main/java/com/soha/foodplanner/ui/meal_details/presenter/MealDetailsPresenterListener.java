package com.soha.foodplanner.ui.meal_details.presenter;

import android.view.View;

import com.soha.foodplanner.data.local.model.CompleteIngredient;
import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.ui.common.presenter.PresenterListener;

import java.util.List;

public interface MealDetailsPresenterListener extends PresenterListener {
    void getMealDetail();

    void setValues(CompleteMeal mealsItem);

    void setIngredients(List<CompleteIngredient> mealsItem, View view);

}
