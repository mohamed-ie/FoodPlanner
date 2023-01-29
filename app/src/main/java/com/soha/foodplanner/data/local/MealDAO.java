package com.soha.foodplanner.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


import com.soha.foodplanner.data.local.entities.FavouriteMeals;
import com.soha.foodplanner.data.local.entities.FavouriteMealsWithMeal;
import com.soha.foodplanner.data.local.entities.Ingredient;
import com.soha.foodplanner.data.local.entities.Meal;
import com.soha.foodplanner.data.local.entities.MealIngredientsRef;
import com.soha.foodplanner.data.local.entities.PlanedMealWithMeal;
import com.soha.foodplanner.data.local.entities.PlannedMealWithMealAndIngredients;
import com.soha.foodplanner.data.local.entities.PlannedMeals;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealDAO {
    @Query("SELECT * FROM meal WHERE id LIKE :id")
    Single<Meal> FindMealById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(Meal meal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertFavMeal(FavouriteMeals meal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertIngredients(Ingredient... ingredients);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMealIngredientsRef(MealIngredientsRef... mealIngredientsRefs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPlannedMeal(PlannedMeals mealIngredientsRefs);

    @Transaction
    @Query("DELETE FROM favourite_meals WHERE meal_id=:id")
    Completable deleteFavouriteMeal(long id);

    @Transaction
    @Query("DELETE FROM planed_meals WHERE id=:id")
    Completable deletePlannedMeal(long id);

    @Transaction
    @Query("SELECT * FROM favourite_meals")
    Flowable<List<FavouriteMealsWithMeal>> getFavouriteMealsWithMeal();

    @Transaction
    @Query("SELECT * FROM planed_meals order by date , mealTime")
    Flowable<List<PlanedMealWithMeal>> getPlanedMealWithMeal();


    @Query("SELECT meal_id FROM favourite_meals")
    Single<List<Long>> getAllFavouriteMealsIds();
}
