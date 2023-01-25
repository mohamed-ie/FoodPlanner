package com.soha.foodplanner.ui.filter.presenter;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.repository.meals.MealsRepository;

public class MealsFilterFactory implements Factory<MealsFilterPresenter> {
    private final MealsRepository mealsRepository;
    private final MealsFilterPresenterListener listener;

    public MealsFilterFactory(MealsRepository mealsRepository, MealsFilterPresenterListener listener) {
        this.mealsRepository = mealsRepository;
        this.listener = listener;
    }

    @Override
    public MealsFilterPresenter create() {
        return new MealsFilterPresenterImpl(mealsRepository, listener);
    }
}
