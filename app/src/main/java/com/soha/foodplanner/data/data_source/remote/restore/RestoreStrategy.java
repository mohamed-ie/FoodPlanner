package com.soha.foodplanner.data.data_source.remote.restore;

import com.soha.foodplanner.data.local.PlannedMeals;

import io.reactivex.rxjava3.core.Flowable;

public interface RestoreStrategy {

    Flowable<Long> restoreFavouriteMeals();
    Flowable<PlannedMeals> restorePlannedMeals();
}
