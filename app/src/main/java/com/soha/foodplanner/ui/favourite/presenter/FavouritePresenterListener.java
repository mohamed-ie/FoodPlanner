package com.soha.foodplanner.ui.favourite.presenter;

import com.soha.foodplanner.data.local.FavouriteMealsWithMeal;
import com.soha.foodplanner.data.local.model.MinMeal;

import java.util.List;

public interface FavouritePresenterListener {
    void getAllFavouriteMeals();
    void addFavMealToAdapter(List<FavouriteMealsWithMeal> minMeals);
    void deleteMealFromFav(FavouriteMealsWithMeal favouriteMealsWithMeal);
}
