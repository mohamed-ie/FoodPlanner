package com.soha.foodplanner.ui.home;

import com.soha.foodplanner.data.remote.common.RemoteDataSource;

public interface HomeRemoteData extends RemoteDataSource {
    void getAllMealsByFirstLetter(char c);


    void getAreas();

    void getAllIngredients();

    void getAllCategories();
}
