package com.soha.foodplanner.data.local.model;

import com.soha.foodplanner.data.local.entities.Meal;

import java.util.List;

public class CompleteMeal{
    private final Meal meal;
    private final List<CompleteIngredient> ingredients;

    public CompleteMeal(Meal meal, List<CompleteIngredient> ingredients) {
        this.meal = meal;
        this.ingredients = ingredients;
    }


    public Meal getMeal() {
        return meal;
    }

    public List<CompleteIngredient> getIngredients() {
        return ingredients;
    }
}
