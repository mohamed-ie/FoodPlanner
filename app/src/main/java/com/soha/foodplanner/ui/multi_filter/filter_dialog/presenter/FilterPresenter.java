package com.soha.foodplanner.ui.multi_filter.filter_dialog.presenter;

import com.soha.foodplanner.ui.common.presenter.Presenter;
import com.soha.foodplanner.ui.multi_filter.Filters;
import com.soha.foodplanner.ui.multi_filter.filter_dialog.FilterState;

public interface FilterPresenter extends Presenter {
    void setFilters(Filters filters);

    void getAllIngredients();

    void getAllAreas();

    void getAllCategories();

    FilterState getState();

    void updateArea(String area);

    void updateCategory(String category);

    void updateIngredient(String ingredient);
}
