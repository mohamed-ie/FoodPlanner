package com.soha.foodplanner.data.data_source.remote.backup;

import com.soha.foodplanner.data.local.PlannedMeals;

import io.reactivex.rxjava3.core.Completable;

public interface BackupStrategy {

    Completable addFavouriteMeal(long mealId);

    Completable addPlannedMeal(PlannedMeals plannedMeals);

}
