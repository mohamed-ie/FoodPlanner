package com.soha.foodplanner.ui.main.home.presenter;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.repository.meals.MealsRepository;

public class HomePresenterFactory implements Factory<HomePresenter<HomePresenterListener>> {
    private final MealsRepository repository;

    public HomePresenterFactory(MealsRepository repository) {
        this.repository = repository;
    }

    @Override
    public HomePresenter<HomePresenterListener> create() {
        return new HomePresenterImpl(repository);
    }
}
