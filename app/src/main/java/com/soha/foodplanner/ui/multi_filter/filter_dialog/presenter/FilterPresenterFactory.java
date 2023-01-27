package com.soha.foodplanner.ui.multi_filter.filter_dialog.presenter;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.repository.meals.MealsRepository;

public class FilterPresenterFactory implements Factory<FilterPresenter> {
    private final FilterPresenterListener listener;
    private final MealsRepository repository;

    public FilterPresenterFactory(FilterPresenterListener listener, MealsRepository repository) {
        this.listener = listener;
        this.repository = repository;
    }

    @Override
    public FilterPresenter create() {
        return new FilterPresenterImpl(repository,listener);
    }
}
