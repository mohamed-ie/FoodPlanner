package com.soha.foodplanner.data.repository.meals;

import android.annotation.SuppressLint;

import com.soha.foodplanner.data.local.entities.FavouriteMeals;
import com.soha.foodplanner.data.local.entities.FavouriteMealsWithMeal;
import com.soha.foodplanner.data.local.entities.Ingredient;
import com.soha.foodplanner.data.local.entities.Meal;
import com.soha.foodplanner.data.local.entities.MealIngredientsRef;
import com.soha.foodplanner.data.local.entities.PlanedMealWithMeal;
import com.soha.foodplanner.data.local.entities.PlannedMeals;
import com.soha.foodplanner.data.local.model.CompleteIngredient;
import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.data.local.model.MinMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
}
