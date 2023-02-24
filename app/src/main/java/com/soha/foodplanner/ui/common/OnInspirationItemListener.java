package com.soha.foodplanner.ui.common;

import com.soha.foodplanner.data.local.model.CompleteMeal;

public interface OnInspirationItemListener {
    void addFavouriteMeal(long id);

    void viewAll(String name);

    void openMealDetails(CompleteMeal completeMeal);
}
