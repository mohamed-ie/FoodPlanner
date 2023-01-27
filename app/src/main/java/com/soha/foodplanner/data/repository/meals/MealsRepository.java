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


    Single<Meal> selectMealById(String id);
    Completable deleteFavMeal(FavouriteMealsWithMeal mealFav);
    Completable deletePlannedMeal(PlanedMealWithMeal planedMealWithMeal);
    Flowable<List<FavouriteMealsWithMeal>> getFavMeal();
    Flowable<List<PlanedMealWithMeal>> getPlanedMeal();
    Completable insertFavMeal(MinMeal minMeal);
    Completable insertPlanMeal(CompleteMeal completeMeal, long date, String mealTime);
    Flowable<Long> getAllFavouriteMealsIds();

    Single<List<MinMeal>> getMealsByIngredient(String ingredient);

    Flowable<Pair<CompleteMeal, Integer>> getAllCompleteMeals();
}
