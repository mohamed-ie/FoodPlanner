package com.soha.foodplanner.data.data_source.remote.user_meals;

import com.soha.foodplanner.data.local.model.CompleteMeal;

import io.reactivex.rxjava3.core.Flowable;

public interface CustomMealsRemoteDataSource {
    String MEALS="meals";

    Flowable<CompleteMeal> getAllMeals();

    void closeAll();
}
