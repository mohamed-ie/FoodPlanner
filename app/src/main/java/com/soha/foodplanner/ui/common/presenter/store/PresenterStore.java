package com.soha.foodplanner.ui.common.presenter.store;

import com.soha.foodplanner.ui.common.presenter.Presenter;

public interface PresenterStore {
    void store(int key, Presenter presenter);
    void remove(int key);
    Presenter get(int key);
}
