package com.soha.foodplanner.ui.search.presenter;

import android.util.Pair;

import com.soha.foodplanner.ui.common.presenter.Presenter;

import java.util.List;

public interface SearchPresenter extends Presenter {
    void searchByName(String name, List<Pair<Long, String>> names);

    void loadAreas();

    void loadIngredients();

    void loadCategories();

}
