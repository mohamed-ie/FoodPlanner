package com.soha.foodplanner.data.remote.home;

import com.soha.foodplanner.data.model.CompleteMeal;
import com.soha.foodplanner.data.remote.common.Failed;
import com.soha.foodplanner.data.remote.common.Loading;

public interface HomeRemoteDataSourceListener extends Loading, Failed {

    void onNextRandomMeal(CompleteMeal completeMeal);
}
