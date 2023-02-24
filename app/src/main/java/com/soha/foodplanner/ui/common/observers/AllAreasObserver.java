package com.soha.foodplanner.ui.common.observers;

import com.soha.foodplanner.ui.main.search.presenter.AllAreasListener;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class  AllAreasObserver implements SingleObserver<List<String>> {
    private final AllAreasListener listener;

    public AllAreasObserver(AllAreasListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        listener.onGetAllAreasLoading();
    }

    @Override
    public void onSuccess(@NonNull List<String> strings) {
        listener.onGetAllAreasSuccess(strings);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        listener.onGetAllAreasError(e.getMessage());
    }
}