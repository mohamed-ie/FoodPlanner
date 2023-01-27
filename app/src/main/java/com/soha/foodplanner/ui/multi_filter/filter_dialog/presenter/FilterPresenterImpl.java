package com.soha.foodplanner.ui.multi_filter.filter_dialog.presenter;

import com.soha.foodplanner.data.repository.meals.MealsRepository;
import com.soha.foodplanner.ui.common.observers.AllAreasObserver;
import com.soha.foodplanner.ui.common.observers.AllCategoriesObserver;
import com.soha.foodplanner.ui.common.observers.AllIngredientsObserver;
import com.soha.foodplanner.ui.multi_filter.Filters;
import com.soha.foodplanner.ui.multi_filter.filter_dialog.FilterState;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class FilterPresenterImpl implements FilterPresenter {
    private final FilterPresenterListener listener;
    private final MealsRepository repository;
    private final FilterState state = new FilterState();

    public FilterPresenterImpl(MealsRepository repository, FilterPresenterListener listener) {
        this.listener = listener;
        this.repository = repository;
    }


    @Override
    public void setFilters(Filters filters) {
        state.setFilters(filters);
    }

    @Override
    public void getAllIngredients() {
        repository.getAllIngredients()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AllIngredientsObserver(listener));
    }

    @Override
    public void getAllAreas() {
        repository.getAllAres()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AllAreasObserver(listener));
    }

    @Override
    public void getAllCategories() {
        repository.getAllCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AllCategoriesObserver(listener));
    }

    @Override
    public FilterState getState() {
        return state;
    }

    @Override
    public void updateArea(String area) {
        if (state.getFilters().getArea().equals(area)) {
            state.getFilters().setArea("");
        }
        state.getFilters().setArea(area);
    }

    @Override
    public void updateCategory(String category) {
        if (state.getFilters().getCategory().equals(category)) {
            state.getFilters().setCategory("");
        }
        state.getFilters().setCategory(category);
    }

    @Override
    public void updateIngredient(String ingredient) {
        if (!state.getFilters().getIngredients().remove(ingredient)) {
            state.getFilters().getIngredients().add(ingredient);
        }
    }
}
