package com.soha.foodplanner.ui.filter.presenter;

import com.soha.foodplanner.ui.common.presenter.Presenter;

public interface MealsFilterPresenter extends Presenter {
    void loadByCategory(String category);
    void loadByArea(String area);

    void searchByName(CharSequence name);

}
