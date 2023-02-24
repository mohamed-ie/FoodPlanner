package com.soha.foodplanner.ui.main.search.presenter;

import com.soha.foodplanner.data.local.model.MinIngredient;

import java.util.List;

public interface AllIngredientsListener {
    void onGetAllIngredientSuccess(List<MinIngredient> ingredients);
    void onGetAllIngredientError(String message);
    void onGetAllIngredientLoading();

}
