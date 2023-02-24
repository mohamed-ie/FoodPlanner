package com.soha.foodplanner.ui.filter.presenter;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.repository.meals.MealsRepository;

public class MealsFilterFactory implements Factory<MealsFilterPresenter> {
    private final MealsRepository mealsRepository;

    public MealsFilterFactory(MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
    }

    @Override
    public MealsFilterPresenter create() {
        return new MealsFilterPresenterImpl(mealsRepository);
    }
}
