package com.soha.foodplanner.ui.multi_filter;

import com.soha.foodplanner.data.local.model.CompleteMeal;

import java.util.ArrayList;
import java.util.List;

public class MultiFilterState {
    private final List<CompleteMeal> meals = new ArrayList<>();
    private final List<String> selectedIngredients = new ArrayList<>();
    private String selectedArea = "";
    private String selectedCategory = "";

    public List<CompleteMeal> getMeals() {
        return meals;
    }

    public List<String> getSelectedIngredients() {
        return selectedIngredients;
    }

    public String getSelectedArea() {
        return selectedArea;
    }

    public void setSelectedArea(String selectedArea) {
        this.selectedArea = selectedArea;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
}
