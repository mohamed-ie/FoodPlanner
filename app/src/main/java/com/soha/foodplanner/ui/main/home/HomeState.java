package com.soha.foodplanner.ui.main.home;

import com.soha.foodplanner.data.local.model.CompleteMeal;

import java.util.ArrayList;
import java.util.List;

public class HomeState {
    private final List<CompleteMeal> inspirations = new ArrayList<>();

    public void insertInspiration(CompleteMeal minMeal) {
        inspirations.add(minMeal);
    }

    public List<CompleteMeal> getInspirations() {
        return inspirations;
    }
}
