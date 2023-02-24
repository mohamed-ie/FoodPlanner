package com.soha.foodplanner.ui.main.home.presenter;

import com.soha.foodplanner.ui.common.presenter.DefaultPresenter;
import com.soha.foodplanner.ui.common.presenter.PresenterListener;

public abstract class HomePresenter<P extends PresenterListener> extends DefaultPresenter<P> {
    public abstract void insertToFavourite(long id);

    public abstract void loadInspirations();


}
