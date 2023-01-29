package com.soha.foodplanner.data.repository.meals;

import android.util.Pair;

import com.soha.foodplanner.data.local.entities.FavouriteMealsWithMeal;
import com.soha.foodplanner.data.local.entities.Meal;
import com.soha.foodplanner.data.local.entities.PlanedMealWithMeal;
import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.data.local.model.MinMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealsRepository {

    Single<List<MinMeal>> getMealsByCategory(String category);

    Single<List<MinMeal>> getMealsByArea(String area);

    Single<List<MinMeal>> loadMeals(int count);

    Single<List<MinMeal>> loadByCategory(String category);

    Single<List<Pair<Long, String>>> searchByFirstLetter(char c);

    Single<List<String>> getAllAres();

    Single<List<MinIngredient>> getAllIngredients();

    Single<List<String>> getAllCategories();


    Single<Meal> selectMealById(long id);

    Completable deleteFavMeal(FavouriteMealsWithMeal mealFav);

    Completable deletePlannedMeal(PlanedMealWithMeal planedMealWithMeal);

    Flowable<List<FavouriteMealsWithMeal>> getFavMeal();

    Flowable<List<PlanedMealWithMeal>> getPlanedMeal();

    Completable insertFavMeal(long id);

    Completable insertPlanMeal(CompleteMeal id, long date, String mealTime);

    Completable insertPlanMeal(long id, long date, String mealTime);

    Single<List<Long>> getAllFavouriteMealsIds();

    Single<List<MinMeal>> getMealsByIngredient(String ingredient);

    Flowable<Pair<CompleteMeal, Integer>> getAllCompleteMeals();

    Completable restoreFavouriteMeals();

    Completable restorePlannedMeals();

    Flowable<List<PlanedMealWithMeal>> getPlannedMeals();

    Single<CompleteMeal> getMealById(long id);

}
