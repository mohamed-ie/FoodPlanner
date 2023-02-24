package com.soha.foodplanner.ui.create_recipe.presenter;

import com.soha.foodplanner.data.repository.meals.MealsRepository;

public class CreateRecipePresenterImpl extends CreateRecipePresenter {
    private final MealsRepository repository;

    public CreateRecipePresenterImpl(MealsRepository repository) {
        this.repository = repository;
    }
}
