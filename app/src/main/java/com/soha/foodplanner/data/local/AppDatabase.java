package com.soha.foodplanner.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.soha.foodplanner.data.local.entities.CachedMeals;
import com.soha.foodplanner.data.local.entities.FavouriteMeals;
import com.soha.foodplanner.data.local.entities.Ingredient;
import com.soha.foodplanner.data.local.entities.Meal;
import com.soha.foodplanner.data.local.entities.MealIngredientsRef;
import com.soha.foodplanner.data.local.entities.PlannedMeals;

@Database(entities = {Meal.class
        , Ingredient.class
        , FavouriteMeals.class
        , CachedMeals.class
        , PlannedMeals.class
        , MealIngredientsRef.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance = null;

    private final static String MEAL_DB_NAME = "THE_MEAL_DB";

    public abstract MealDAO mealDAO();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            MEAL_DB_NAME).build();
        }
        return instance;
    }
}