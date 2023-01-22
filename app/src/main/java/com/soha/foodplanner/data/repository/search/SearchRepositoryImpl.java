package com.soha.foodplanner.data.repository.search;

import com.soha.foodplanner.data.remote.search.SearchRemoteDataSource;

public class SearchRepositoryImpl implements SearchRepository {
    private final SearchRemoteDataSource searchRemoteDataSource;

    public SearchRepositoryImpl(SearchRemoteDataSource searchRemoteDataSource) {
        this.searchRemoteDataSource = searchRemoteDataSource;
    }

    @Override
    public void searchByFirstLetter(char c) {
        searchRemoteDataSource.getAllMealsByFirstLetter(c);
    }

    @Override
    public void getAllAres() {
            searchRemoteDataSource.getAreas();
    }

    @Override
    public void getAllIngredients() {
        searchRemoteDataSource.getAllIngredients();
    }

    @Override
    public void getAllCategories() {
        searchRemoteDataSource.getAllCategories();
    }
}
