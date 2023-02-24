package com.soha.foodplanner.ui.common.observers;

import com.soha.foodplanner.ui.main.search.presenter.AllCategoriesListener;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class AllCategoriesObserver implements SingleObserver<List<String>> {
    private final AllCategoriesListener listener;

    public AllCategoriesObserver(AllCategoriesListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        listener.onGetAllCategoriesLoading();
    }

    @Override
    public void onSuccess(@NonNull List<String> strings) {
        listener.onGetAllCategoriesSuccess(strings);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        listener.onGetAllCategoriesError(e.getMessage());
    }
}
