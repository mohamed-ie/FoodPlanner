package com.soha.foodplanner.ui.create_recipe.presenter;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.repository.meals.MealsRepository;

public class CreateRecipePresenterFactory implements Factory<CreateRecipePresenter> {
    private final MealsRepository repository;

    public CreateRecipePresenterFactory(MealsRepository repository) {
        this.repository = repository;
    }

    @Override
    public CreateRecipePresenter create() {
        return new CreateRecipePresenterImpl(repository);
    }
}
