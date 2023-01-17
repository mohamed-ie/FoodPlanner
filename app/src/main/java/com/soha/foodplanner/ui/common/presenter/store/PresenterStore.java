package com.soha.foodplanner.ui.common.presenter.store;

import com.soha.foodplanner.ui.common.presenter.Presenter;

public interface PresenterStore {
    void store(String key, Presenter presenter);
    void remove(String key);
    Presenter get(String key);
}
