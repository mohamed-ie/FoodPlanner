package com.soha.foodplanner.ui.multi_filter.presenter;

import com.soha.foodplanner.ui.common.presenter.DefaultPresenter;

public abstract class MultiFilterPresenter extends DefaultPresenter<MultiFilterPresenterListener> {
    public abstract void getAllMeals();

    public abstract void searchByName(CharSequence name);
}
