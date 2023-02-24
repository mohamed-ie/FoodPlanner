package com.soha.foodplanner.ui.main.favourite.presenter;

import com.soha.foodplanner.data.local.entities.FavouriteMealsWithMeal;

import java.util.List;

public interface FavouritePresenterListener {
    void getAllFavouriteMeals();
    void addFavMealToAdapter(List<FavouriteMealsWithMeal> minMeals);
    void deleteMealFromFav(FavouriteMealsWithMeal favouriteMealsWithMeal);
}
