package com.soha.foodplanner.ui.search.presenter;


import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.data.repository.meals.MealsRepository;
import com.soha.foodplanner.ui.search.SearchState;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchPresenter {
    private final MealsRepository repository;
    private final SearchPresenterListener listener;
    private final SearchState state = new SearchState();

    public SearchPresenterImpl(MealsRepository repository, SearchPresenterListener listener) {
        this.repository = repository;
        this.listener = listener;
    }

    @Override
    public void searchByName(String name, List<String> names) {
        if (name.isEmpty()) {
            listener.onSearchSuccess(new ArrayList<>());
            state.setSearch(new ArrayList<>());
            return;
        }

        if (state.getSearch().isEmpty()) {
            if (names.isEmpty()) {
                searchByFirstLetter(name.charAt(0));
                return;
            }

            state.setSearch(names);
        }

        if (name.substring(0, 1).equalsIgnoreCase(state.getSearch().get(0).substring(0, 1))) {
            performSearch(name);
            return;
        }

        searchByFirstLetter(name.charAt(0));
        state.setSearch(new ArrayList<>());
    }

    private void searchByFirstLetter(char c) {
        repository.searchByFirstLetter(c)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SearchObserver());
    }

    @Override
    public void loadAreas() {
        repository.getAllAres()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadAllAreasObserver());
    }

    @Override
    public void loadIngredients() {
        repository.getAllIngredients()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadAllIngredientsObserver());
    }

    @Override
    public void loadCategories() {
        repository.getAllCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadAllCategoriesObserver());
    }

    private void performSearch(String key) {
        Flowable.fromIterable(state.getSearch())
                .subscribeOn(Schedulers.computation())
                .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(key.toLowerCase(Locale.ROOT)))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SearchObserver());

    }

    private class LoadAllAreasObserver implements SingleObserver<List<String>> {
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

    private class LoadAllCategoriesObserver implements SingleObserver<List<String>> {

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

    private class LoadAllIngredientsObserver implements SingleObserver<List<MinIngredient>> {

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            listener.onGetAllIngredientLoading();
        }

        @Override
        public void onSuccess(@NonNull List<MinIngredient> strings) {
            listener.onGetAllIngredientSuccess(strings);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            listener.onGetAllIngredientError(e.getMessage());
        }
    }

    private class SearchObserver implements SingleObserver<List<String>> {

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

}
