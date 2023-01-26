package com.soha.foodplanner.ui.favourite.presenter;

import android.annotation.SuppressLint;

import com.soha.foodplanner.data.data_source.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.data_source.remote.webservice.Webservice;
import com.soha.foodplanner.data.local.FavouriteMealsWithMeal;
import com.soha.foodplanner.data.repository.Repository;
import com.soha.foodplanner.ui.favourite.FavAdapter;
import com.soha.foodplanner.ui.home.presenter.HomePresenterListener;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritePresenter {
    FavouritePresenterListener favouritePresenterListener;
    TheMealDBWebService theMealDBWebService;

    public FavouritePresenter(FavouritePresenterListener favouritePresenterListener) {
        this.favouritePresenterListener = favouritePresenterListener;
        theMealDBWebService = Webservice.getInstance().getTheMealDBWebService();
    }

    @SuppressLint("CheckResult")
    public void getFavourites(Repository repo){
        repo.getFavMeal().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Consumer<? super List<FavouriteMealsWithMeal>>) new Consumer<List<FavouriteMealsWithMeal>>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void accept(List<FavouriteMealsWithMeal> meals) throws Throwable {

                        favouritePresenterListener.addFavMealToAdapter(meals);

                    }
                });
    }
}
