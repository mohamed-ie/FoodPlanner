package com.soha.foodplanner.ui.view_all_categories.presenter;

import com.soha.foodplanner.data.local.model.MinMeal;

import java.util.List;

public interface ViewAllCatListener {

    void addCategoryMealsToAdapter(List<MinMeal> minMeal);
}
