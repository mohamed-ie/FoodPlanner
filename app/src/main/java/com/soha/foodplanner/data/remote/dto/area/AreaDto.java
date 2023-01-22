package com.soha.foodplanner.data.remote.dto.area;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class AreaDto {

    @SerializedName("meals")
    private List<AreaItem> meals;

    public List<AreaItem> getMeals() {
        return meals;
    }
}