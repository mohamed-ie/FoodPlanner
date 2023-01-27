package com.soha.foodplanner.ui.multi_filter.presenter;

import com.soha.foodplanner.ui.common.presenter.Presenter;

public interface MultiFilterPresenter extends Presenter {
    void getAllMeals();

    void searchByName(CharSequence name);
}
