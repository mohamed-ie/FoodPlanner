package com.soha.foodplanner.ui.common.presenter.factory;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.ui.common.presenter.Presenter;

public interface PresenterFactory<P extends Presenter> {

    P create(String key, Factory<P> factory);

    void onDestroy(String key,boolean isChangingConfigurations);
}
