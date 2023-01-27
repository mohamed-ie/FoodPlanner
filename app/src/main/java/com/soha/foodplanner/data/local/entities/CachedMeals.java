package com.soha.foodplanner.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "cached_meals")
public class CachedMeals {

    @PrimaryKey(autoGenerate = true)
    private byte id;
    private String type;
    @ColumnInfo(name = "meal_id")
    private long mealId;

    public CachedMeals(byte id, String type, long mealId, String name) {
        this.id = id;
        this.type = type;
        this.mealId = mealId;
        this.name = name;
    }

    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

}
