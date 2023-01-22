package com.soha.foodplanner.ui.search.presenter;

import androidx.annotation.NonNull;

import com.soha.foodplanner.data.model.MinIngredient;
import com.soha.foodplanner.data.repository.search.SearchRepository;
import com.soha.foodplanner.ui.search.SearchState;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchPresenter {
    private final SearchRepository searchRepository;
    private final SearchPresenterListener searchPresenterListener;
    private final SearchState state = new SearchState();

    public SearchPresenterImpl(SearchRepository searchRepository, SearchPresenterListener searchPresenterListener) {
        this.searchRepository = searchRepository;
        this.searchPresenterListener = searchPresenterListener;
    }

    @Override
    public void searchByName(@NonNull String name, @NonNull List<String> names) {
        if (name.isEmpty()) {
            searchPresenterListener.onSearchSuccess(new ArrayList<>());
            state.setSearch(new ArrayList<>());
            return;
        }

        if (state.getSearch().isEmpty()) {
            if (names.isEmpty()) {
                searchRepository.searchByFirstLetter(name.charAt(0));
                return;
            }

            state.setSearch(names);
        }

        if (name.substring(0, 1).equalsIgnoreCase(state.getSearch().get(0).substring(0, 1))) {
            performSearch(name);
            return;
        }

        searchRepository.searchByFirstLetter(name.charAt(0));
        state.setSearch(new ArrayList<>());
    }

    @Override
    public void loadAreas() {
        searchRepository.getAllAres();
    }

    @Override
    public void loadIngredients() {
        searchRepository.getAllIngredients();
    }

    @Override
    public void loadCategories() {
        searchRepository.getAllCategories();
    }

    private void performSearch(String key) {
        Disposable searchDisposable = Flowable
                .fromIterable(state.getSearch())
                .subscribeOn(Schedulers.computation())
                .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(key.toLowerCase(Locale.ROOT)))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchPresenterListener::onSearchSuccess,
                        throwable -> searchPresenterListener.onFailed(throwable.getMessage()));

    }
}
