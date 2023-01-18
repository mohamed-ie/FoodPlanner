package com.soha.foodplanner.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealDAO {
    @Query("SELECT * FROM meal")
    Single<List<Meal>> getAllMeals();


    @Query("SELECT * FROM meal WHERE category LIKE :mealCategory")
    Single<List<Meal>> FindMealByCategory(String mealCategory);

    @Query("SELECT * FROM meal WHERE area LIKE :mealArea")
    Single<List<Meal>> FindMealByArea(String mealArea);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(Meal meal);

    @Delete
    Completable deleteMeal(Meal meal);

    @Transaction
    @Query("SELECT * FROM meal")
    public Single<List<FavouriteMealsWithMeal>> getFavouriteMealsWithMeal();

    @Transaction
    @Query("SELECT * FROM meal")
    public Single<List<PlanedMealWithMeal>> getPlanedMealWithMeal();

    @Transaction
    @Query("SELECT * FROM ingredient")
    public Single<List<IngredientWithMeal>> getIngredientWithMeal();

    @Transaction
    @Query("SELECT * FROM meal")
    public Single<List<MealWithIngredient>> getMealWithIngredient();



}
