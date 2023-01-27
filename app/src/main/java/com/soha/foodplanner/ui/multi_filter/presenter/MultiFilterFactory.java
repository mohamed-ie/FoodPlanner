package com.soha.foodplanner.ui.multi_filter.presenter;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.repository.meals.MealsRepository;

public class MultiFilterFactory implements Factory<MultiFilterPresenter> {
    private final MealsRepository repository;
    private final MultiFilterPresenterListener listener;

    public MultiFilterFactory(MealsRepository mealsRepository, MultiFilterPresenterListener listener) {
        this.repository = mealsRepository;
        this.listener = listener;
    }


    @Override
    public MultiFilterPresenter create() {
        return new MultiFilterPresenterImpl(repository, listener);
    }
}
