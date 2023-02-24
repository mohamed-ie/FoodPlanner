package com.soha.foodplanner.ui.main.planned;

import com.soha.foodplanner.data.local.entities.PlanedMealWithMeal;

import java.util.List;

public interface PlannedPresenterListener {

    void onNextItem(List<PlanedMealWithMeal> plannedMeals);

}
