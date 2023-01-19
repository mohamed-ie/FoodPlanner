package com.soha.foodplanner.data.repository.home;

import com.soha.foodplanner.data.remote.home.HomeRemoteDataSource;

public class HomeRepositoryImpl implements HomeRepository{
    private final HomeRemoteDataSource remoteDataSource;
    private int count;

    public HomeRepositoryImpl(HomeRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void loadMeals(int count) {
        remoteDataSource.getRandomMeals(count);
    }
}
