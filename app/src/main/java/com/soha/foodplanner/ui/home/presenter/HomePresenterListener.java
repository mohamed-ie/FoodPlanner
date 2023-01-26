package com.soha.foodplanner.ui.home.presenter;

import com.soha.foodplanner.data.local.model.MinMeal;

import java.util.List;

public interface HomePresenterListener {
    void getCategories();
    void getCategoryMeals(List<String> categoryItemList);
    void addMealToAdapter(List<MinMeal> minMeals,String s);

}
