package com.soha.foodplanner.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(tableName = "planed_meals")
public class PlannedMeals {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "meal_id")

    private long mealId;
    //private long date;
    private String mealDay;


    public PlannedMeals(long id, long mealId,  String mealDay) {
        this.id = id;
        this.mealId = mealId;
        //this.date = date;
        this.mealDay = mealDay;
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

//    public long getDate() {
//        return date;
//    }
//
//    public void setDate(long date) {
//        this.date = date;
//    }

    public String getMealDay() {
        return mealDay;
    }

    public void setMealDay(String mealDay) {
        this.mealDay = mealDay;
    }
}
