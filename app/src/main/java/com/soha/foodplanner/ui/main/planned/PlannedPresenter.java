package com.soha.foodplanner.ui.main.planned;

public interface PlannedPresenter {

    void loadPlannedMeals();

    void remove(long id);

    void checkRemoteData();

}
