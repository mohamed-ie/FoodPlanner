package com.soha.foodplanner.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "planed_meals")
public class PlannedMeals {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "meal_id")
    private long mealId;
    private long date;
    private String mealTime;


    @Ignore
    public PlannedMeals(long mealId, long date, String mealTime) {
        this.mealId = mealId;
        this.date = date;
        this.mealTime = mealTime;
    }

    public PlannedMeals() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getMealTime() {
        return mealTime;
    }

    public void setMealTime(String mealTime) {
        this.mealTime = mealTime;
    }
}
