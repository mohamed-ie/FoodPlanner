package com.soha.foodplanner.ui.filter;

import com.soha.foodplanner.data.local.model.MinMeal;

import java.util.List;

public class MealsFilterState {
    private List<MinMeal> minMeals ;

    public MealsFilterState() {
    }

    public void setMinMeals(List<MinMeal> minMeals) {
        this.minMeals = minMeals;
    }

    public List<MinMeal> getMinMeals() {
        return minMeals;
    }
}
