package com.soha.foodplanner.data.repository.meals;

import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.data.local.model.MinMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface MealsRepository {

    Single<List<MinMeal>> getMealsByCategory(String category);

    Single<List<MinMeal>> getMealsByArea(String area);

    Single<List<MinMeal>> loadMeals(int count);

    Single<List<MinMeal>> loadByCategory(String category);

    Single<List<String>> searchByFirstLetter(char c);

    Single<List<String>> getAllAres();

    Single<List<MinIngredient>> getAllIngredients();

    Single<List<String>> getAllCategories();
}
