package com.soha.foodplanner.ui.common.presenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

public interface Presenter {
    List<Disposable> disposables = new ArrayList<>();

    default void destroy() {
        disposables.forEach(Disposable::dispose);
        disposables.clear();
    }
}
