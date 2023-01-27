package com.soha.foodplanner.ui.local_details.presenter;

import android.view.View;

import com.soha.foodplanner.data.local.entities.Meal;

public interface LocalDetailsListener {
    void getLocalMealDetail();
    void setLocalValues(Meal meal);
}
