package com.soha.foodplanner.data.repository;

import android.annotation.SuppressLint;

import com.soha.foodplanner.data.local.AppDatabase;
import com.soha.foodplanner.data.local.entities.FavouriteMeals;
import com.soha.foodplanner.data.local.entities.FavouriteMealsWithMeal;
import com.soha.foodplanner.data.local.entities.Ingredient;
import com.soha.foodplanner.data.local.entities.IngredientWithMeal;
import com.soha.foodplanner.data.local.entities.Meal;
import com.soha.foodplanner.data.local.MealDAO;
import com.soha.foodplanner.data.local.entities.MealIngredientsRef;
import com.soha.foodplanner.data.local.entities.PlanedMealWithMeal;
import com.soha.foodplanner.data.local.entities.PlannedMeals;
import com.soha.foodplanner.data.local.model.CompleteIngredient;
import com.soha.foodplanner.data.local.model.CompleteMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsLocalDataSource {
    private final MealDAO mealDAO;

    public MealsLocalDataSource(AppDatabase appDatabase) {
        this.mealDAO = appDatabase.mealDAO();
    }


    public Single<IngredientWithMeal> selectMealById(long id) {
        return mealDAO.FindMealById(id);
    }

    public Completable deleteFavMeal(FavouriteMealsWithMeal mealFav) {
        return mealDAO.deleteFavouriteMeal(mealFav.getMeal().getId())
                .subscribeOn(Schedulers.io());
    }

    public Completable deletePlannedMeal(long id) {
        return mealDAO.deletePlannedMeal(id);
    }


    public Flowable<List<FavouriteMealsWithMeal>> getFavMeal() {
        return mealDAO.getFavouriteMealsWithMeal()
                .subscribeOn(Schedulers.io());
    }

    public Flowable<List<PlanedMealWithMeal>> getPlanedMeal() {
        return mealDAO.getPlanedMealWithMeal();
    }


    public Completable insertFavMeal(CompleteMeal completeMeal) {
        //insert favourite meal
        return insertMealWithIngredients(completeMeal)
                .andThen(mealDAO.insertFavMeal(new FavouriteMeals(completeMeal.getMeal().getId())));
    }

    private Completable insertMealWithIngredients(CompleteMeal completeMeal) {
        Meal meal = completeMeal.getMeal();
        List<CompleteIngredient> completeIngredients = completeMeal.getIngredients();
        //insert meal 1st
        Completable completable = mealDAO.insertMeal(completeMeal.getMeal());

        for (CompleteIngredient completeIngredient : completeIngredients)
            //insert ingredient 2nd
            completable = completable.andThen(mealDAO.insertIngredients(new Ingredient(completeIngredient.getName(), completeIngredient.getThumbnailUrl())))
                    //insert ingredient-meal relationship 3rd
                    .andThen(mealDAO.insertMealIngredientsRef(new MealIngredientsRef(meal.getId(), completeIngredient.getName(), completeIngredient.getMeasure())));
        return completable;
    }


    @SuppressLint("CheckResult")
    public Completable insertPlanMeal(CompleteMeal completeMeal, long date, String mealTime) {
        return insertMealWithIngredients(completeMeal)
                .andThen(mealDAO.insertPlannedMeal(new PlannedMeals(completeMeal.getMeal().getId(), date, mealTime)));

    }

    public Single<List<Long>> getAllFavouriteMealsIds() {
        return mealDAO.getAllFavouriteMealsIds();
    }

    public Completable insertMeal(Meal meal) {
        return mealDAO.insertMeal(meal);
    }

    public Completable insertIngredient(Ingredient ingredient) {
        return mealDAO.insertIngredients(ingredient);
    }
    public Completable insertFavouriteMeal(FavouriteMeals favouriteMeals){
        return mealDAO.insertFavMeal(favouriteMeals);
    }
}
