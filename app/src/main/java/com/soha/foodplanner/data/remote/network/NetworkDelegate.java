package com.soha.foodplanner.data.remote.network;

import java.util.List;

public interface NetworkDelegate {
    public void onSuccessResult(List<MealsItem> mealsList);
    public void onFailureResult(String errorMsg);
}
