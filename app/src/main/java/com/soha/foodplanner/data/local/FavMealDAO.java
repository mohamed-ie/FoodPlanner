package com.soha.foodplanner.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.soha.foodplanner.data.remote.network.MealsItem;

import java.util.List;
@Dao
public interface FavMealDAO {
    @Query("SELECT * FROM MealsItem")
    LiveData<List<MealsItem>> getAllMeals();

    @Query("SELECT * FROM MealsItem WHERE strMeal LIKE :mealName")
    MealsItem FindMealByName(String mealName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(MealsItem meal);

    @Delete
    void deleteMeal(MealsItem meal);


    }


