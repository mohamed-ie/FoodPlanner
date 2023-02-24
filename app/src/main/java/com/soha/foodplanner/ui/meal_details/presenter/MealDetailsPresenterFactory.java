package com.soha.foodplanner.ui.meal_details.presenter;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.repository.meals.MealsRepository;

public class MealDetailsPresenterFactory implements Factory<MealDetailsPresenter> {
    private final MealsRepository repository;

    public MealDetailsPresenterFactory(MealsRepository repository) {
        this.repository = repository;
    }

    @Override
    public MealDetailsPresenter create() {
        return new MealDetailsPresenterImpl(repository);
    }
}
