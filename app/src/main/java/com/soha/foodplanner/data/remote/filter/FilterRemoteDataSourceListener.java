package com.soha.foodplanner.data.remote.filter;

import com.soha.foodplanner.data.model.MinMeal;
import com.soha.foodplanner.data.remote.common.Failed;
import com.soha.foodplanner.data.remote.common.Loading;

import java.util.List;

public interface FilterRemoteDataSourceListener extends Failed, Loading {
    void onSuccess(List<MinMeal> meals);
}
