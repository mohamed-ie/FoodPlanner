package com.soha.foodplanner.ui.filter.presenter;

import com.soha.foodplanner.ui.common.presenter.DefaultPresenter;

public abstract class MealsFilterPresenter extends DefaultPresenter<MealsFilterPresenterListener> {
    public abstract void loadByCategory(String category);
    public abstract void loadByArea(String area);
    public abstract void searchByName(CharSequence name);
    public abstract void loadByIngredient(String ingredient);
    public abstract void addToFavourite(long name);
}
