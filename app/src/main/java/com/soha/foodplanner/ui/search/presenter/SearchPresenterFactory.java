package com.soha.foodplanner.ui.search.presenter;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.repository.meals.MealsRepository;

public class SearchPresenterFactory implements Factory<SearchPresenter> {
    private final MealsRepository repository;
    private final SearchPresenterListener searchPresenterListener;

    public SearchPresenterFactory(MealsRepository repository, SearchPresenterListener searchPresenterListener) {
        this.repository = repository;
        this.searchPresenterListener = searchPresenterListener;
    }

    @Override
    public SearchPresenter create() {
        return new SearchPresenterImpl(repository, searchPresenterListener);
    }
}
