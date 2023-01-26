package com.soha.foodplanner.ui.home;

import com.soha.foodplanner.data.local.model.MinMeal;

import java.util.List;

public class CategoryWithMeals {
    private final String name;
    private final List<MinMeal> meals;

    public CategoryWithMeals(String name, List<MinMeal> meals) {
        this.name = name;
        this.meals = meals;
    }

    public String getName() {
        return name;
    }

    public List<MinMeal> getMeals() {
        return meals;
    }
}
