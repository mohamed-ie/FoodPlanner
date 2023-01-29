package com.soha.foodplanner.ui.search.presenter;


import android.util.Pair;

import com.soha.foodplanner.data.repository.meals.MealsRepository;
import com.soha.foodplanner.ui.common.observers.AllAreasObserver;
import com.soha.foodplanner.ui.common.observers.AllCategoriesObserver;
import com.soha.foodplanner.ui.common.observers.AllIngredientsObserver;
import com.soha.foodplanner.ui.common.observers.SearchObserver;
import com.soha.foodplanner.ui.search.SearchState;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
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
    public void searchByName(String name, List<Pair<Long, String>> names) {
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

        if (name.substring(0, 1).equalsIgnoreCase(state.getSearch().get(0).second.substring(0, 1))) {
            performSearch(name);
            return;
        }

        searchByFirstLetter(name.charAt(0));
        state.setSearch(new ArrayList<>());
    }

    private void searchByFirstLetter(char c) {
        repository.searchByFirstLetter(c)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SearchObserver(listener));
    }

    @Override
    public void loadAreas() {
        repository.getAllAres()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AllAreasObserver(listener));
    }

    @Override
    public void loadIngredients() {
        repository.getAllIngredients()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AllIngredientsObserver(listener));
    }

    @Override
    public void loadCategories() {
        repository.getAllCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AllCategoriesObserver(listener));
    }

    private void performSearch(String key) {
        Flowable.fromIterable(state.getSearch())
                .subscribeOn(Schedulers.computation())
                .filter(name -> name.second.toLowerCase(Locale.ROOT).startsWith(key.toLowerCase(Locale.ROOT)))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SearchObserver(listener));

    }

}
