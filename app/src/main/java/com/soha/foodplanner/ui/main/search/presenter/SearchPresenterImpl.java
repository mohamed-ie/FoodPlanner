package com.soha.foodplanner.ui.main.search.presenter;


import android.util.Pair;

import com.soha.foodplanner.data.repository.meals.MealsRepository;
import com.soha.foodplanner.ui.main.search.SearchState;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImpl extends SearchPresenter {
    private final MealsRepository repository;
    private final SearchState state = new SearchState();

    public SearchPresenterImpl(MealsRepository repository) {
        this.repository = repository;
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
        listener.onSearchLoading();
        disposables.add(repository.searchByFirstLetter(c)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pairs -> {
                    listener.onSearchSuccess(pairs);
                    state.setSearch(pairs);
                }, throwable -> {

                }));
    }

    @Override
    public void loadAreas() {
        if (state.getAreas()==null) {
            listener.onGetAllAreasLoading();
            disposables.add(repository.getAllAres()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(areas -> {
                        listener.onGetAllAreasSuccess(areas);
                        state.setAreas(areas);
                    }, throwable -> {

                    }));
        } else listener.onGetAllAreasSuccess(state.getAreas());
    }

    @Override
    public void loadIngredients() {
        if (state.getMinIngredients()==null) {
            listener.onGetAllIngredientLoading();
            disposables.add(repository.getAllIngredients()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(minIngredients -> {
                        listener.onGetAllIngredientSuccess(minIngredients);
                        state.setMinIngredients(minIngredients);
                    }, throwable -> {

                    }));
        } else listener.onGetAllIngredientSuccess(state.getMinIngredients());
    }

    @Override
    public void loadCategories() {
        if (state.getCategories()==null) {
            listener.onGetAllCategoriesLoading();
            disposables.add(repository.getAllCategories()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(categories -> {
                        listener.onGetAllCategoriesSuccess(categories);
                        state.setCategories(categories);
                    }, throwable -> {

                    }));
        } else listener.onGetAllCategoriesSuccess(state.getCategories());
    }

    private void performSearch(String key) {
        disposables.add(Flowable.fromIterable(state.getSearch())
                .subscribeOn(Schedulers.computation())
                .filter(name -> name.second.toLowerCase(Locale.ROOT).startsWith(key.toLowerCase(Locale.ROOT)))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pairs -> {
                    listener.onSearchSuccess(pairs);
                    state.setSearch(pairs);
                }));

    }

}
