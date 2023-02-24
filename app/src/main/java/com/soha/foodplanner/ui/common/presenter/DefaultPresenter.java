package com.soha.foodplanner.ui.common.presenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

public abstract class DefaultPresenter<P extends PresenterListener> implements Presenter {
    protected List<Disposable> disposables = new ArrayList<>();
    protected P listener;


    public void setListener(P listener) {
        this.listener=listener;
    }

    @Override
    public void destroy() {
        disposables.forEach(Disposable::dispose);
    }
}
