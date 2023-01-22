package com.soha.foodplanner.data.remote.search;

import com.soha.foodplanner.data.remote.common.RemoteDataSource;
import com.soha.foodplanner.data.remote.home.HomeRemoteDataSource;

public interface SearchRemoteDataSource extends RemoteDataSource {

    void getAllMealsByFirstLetter(char c);


    void getAreas();

    void getAllIngredients();

    void getAllCategories();
}
