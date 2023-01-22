package com.soha.foodplanner.ui.search.presenter;

import com.soha.foodplanner.ui.common.presenter.Presenter;
import com.soha.foodplanner.ui.search.SearchState;

import java.util.List;

public interface SearchPresenter extends Presenter {
    void searchByName(String name, List<String> names);

    void loadAreas();

    void loadIngredients();

    void loadCategories();

}
