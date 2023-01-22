package com.soha.foodplanner.data.remote.filter;

import com.soha.foodplanner.data.remote.common.RemoteDataSource;

public interface FilterRemoteDataSource extends RemoteDataSource {
    void getAreaMeals(String area);
}
