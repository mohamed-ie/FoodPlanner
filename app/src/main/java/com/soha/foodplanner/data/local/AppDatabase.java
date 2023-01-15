package com.soha.foodplanner.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MealsItem.class},version =1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance = null;

    public abstract FavMealDAO favMealDAO();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "FavMeal").build();
        }
        return instance;
    }
}