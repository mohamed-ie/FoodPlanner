package com.soha.foodplanner.ui.main.search.presenter;

import android.util.Pair;

import com.soha.foodplanner.ui.common.presenter.DefaultPresenter;

import java.util.List;

public abstract class SearchPresenter extends DefaultPresenter<SearchPresenterListener> {
    public abstract void searchByName(String name, List<Pair<Long, String>> names);

    public abstract void loadAreas();

    public abstract void loadIngredients();

    public abstract void loadCategories();

}
