package com.soha.foodplanner.ui.local_details.presenter;

import android.view.View;

import com.soha.foodplanner.data.repository.Repository;

public class LocalDetailsPresenter {

    LocalDetailsListener localDetailsListener;
    Repository repo;
    View view;

    public LocalDetailsPresenter(LocalDetailsListener localDetailsListener, Repository repo,View view) {
        this.localDetailsListener = localDetailsListener;
        this.repo = repo;
        this.view=view;
    }
    public void getLocalDetails(String mealIdStr){
        repo.selectMealById(mealIdStr,localDetailsListener,view);
    }


}
