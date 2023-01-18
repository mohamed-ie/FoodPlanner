package com.soha.foodplanner.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Meal.class
        ,Ingredient.class
        ,FavouriteMeals.class
        ,CachedMeals.class
        ,PlannedMeals.class
        ,MealIngredientsRef.class
},version =1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance = null;

    public abstract MealDAO mealDAO();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext()
                            , AppDatabase.class
                            , "PlanedMeals")
            .build();
        }
        return instance;
    }
}