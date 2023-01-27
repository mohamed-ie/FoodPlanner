package com.soha.foodplanner.data.data_source.remote.meals;

import android.util.Pair;

import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.data_source.remote.RemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealsRemoteDataSource extends RemoteDataSource {

    Single<List<MinMeal>> getMealsByCategory(String category);

    Single<List<MinMeal>> getMealsByArea(String area);

    Single<List<MinMeal>> getAreaMeals(String area);

    Single<List<String>> getAllMealsByFirstLetter(char c);

    Single<List<String>> getAreas();

    Single<List<MinIngredient>> getAllIngredients();

    Single<List<String>> getAllCategories();

    Single<List<MinMeal>> getAllMealsByIngredient(String ingredient);

    Flowable<Pair<CompleteMeal, Integer>> getAllCompleteMeals();
}
