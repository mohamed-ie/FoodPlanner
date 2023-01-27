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
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.DELETE;

@Dao
public interface MealDAO {
//    @Query("SELECT * FROM meal")
//    Single<List<Meal>> getAllMeals();
//
//
//    @Query("SELECT * FROM meal WHERE category LIKE :mealCategory")
//    Single<List<Meal>> FindMealByCategory(String mealCategory);
//
 //  @Query("SELECT * FROM meal WHERE area LIKE :mealArea")
//    Single<List<Meal>> FindMealByArea(String mealArea);
//



    @Query("SELECT * FROM meal WHERE id LIKE :id")
    Single<Meal> FindMealById(String id);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(Meal meal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertFavMeal(FavouriteMeals meal);

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    Completable insertIngredients(Ingredient ... ingredients);
    @Insert(onConflict =OnConflictStrategy.IGNORE)
    Completable insertMealIngredientsRef(MealIngredientsRef ... mealIngredientsRefs);
    @Insert(onConflict =OnConflictStrategy.REPLACE)
    Completable insertPlannedMeal(PlannedMeals mealIngredientsRefs);



    @Transaction
    @Query("DELETE FROM favourite_meals WHERE meal_id=:id")
    Completable deleteFavouriteMeal(long id);

    @Delete
    Completable deleteIngredients(Ingredient ... ingredients);
    @Query("DELETE FROM MealIngredientsRef WHERE id=:id ")
    Completable deleteMealIngredientsRef(long ... id);


    @Delete
    Completable deletePlanMeal(Meal meal);

    @Transaction
    @Query("SELECT * FROM favourite_meals")
    Flowable<List<FavouriteMealsWithMeal>> getFavouriteMealsWithMeal();

    @Transaction
    @Query("SELECT * FROM planed_meals")
    Flowable<List<PlanedMealWithMeal>> getPlanedMealWithMeal();


//    @Transaction
//    @Query("SELECT * FROM ingredient")
//    Single<List<IngredientWithMeal>> getIngredientWithMeal();
//
//    @Transaction
//    @Query("SELECT * FROM meal")
//    public Single<List<MealWithIngredient>> getMealWithIngredient();
//


}
