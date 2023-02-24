package com.soha.foodplanner.ui.main.search.presenter;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.repository.meals.MealsRepository;

public class SearchPresenterFactory implements Factory<SearchPresenter> {
    private final MealsRepository repository;

    public SearchPresenterFactory(MealsRepository repository) {
        this.repository = repository;
    }

    @Override
    public SearchPresenter create() {
        return new SearchPresenterImpl(repository);
    }
}
