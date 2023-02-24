package com.soha.foodplanner.ui.common.observers;

import android.util.Pair;

import com.soha.foodplanner.ui.main.search.presenter.SearchListener;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class SearchObserver implements SingleObserver<List<Pair<Long, String>>> {
    private final SearchListener listener;

    public SearchObserver(SearchListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        listener.onSearchLoading();
    }

    @Override
    public void onSuccess(@NonNull List<Pair<Long, String>> strings) {
        listener.onSearchSuccess(strings);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        listener.onSearchError(e.getMessage());
    }
}