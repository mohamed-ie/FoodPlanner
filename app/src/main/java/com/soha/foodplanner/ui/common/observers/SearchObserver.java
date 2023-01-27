package com.soha.foodplanner.ui.common.observers;

import com.soha.foodplanner.ui.search.presenter.SearchListener;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class SearchObserver implements SingleObserver<List<String>> {
    private final SearchListener listener;

    public SearchObserver(SearchListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        listener.onSearchLoading();
    }

    @Override
    public void onSuccess(@NonNull List<String> strings) {
        listener.onSearchSuccess(strings);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        listener.onSearchError(e.getMessage());
    }
}