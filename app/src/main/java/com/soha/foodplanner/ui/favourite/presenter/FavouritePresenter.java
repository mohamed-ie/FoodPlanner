package com.soha.foodplanner.ui.favourite.presenter;

import android.annotation.SuppressLint;

import com.soha.foodplanner.data.local.entities.FavouriteMealsWithMeal;
import com.soha.foodplanner.data.repository.MealsLocalDataSource;
import com.soha.foodplanner.data.repository.meals.MealsRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritePresenter {
    private final FavouritePresenterListener favouritePresenterListener;
    private final MealsRepository mealsRepository;

    public FavouritePresenter(FavouritePresenterListener favouritePresenterListener, MealsRepository mealsRepository) {
        this.favouritePresenterListener = favouritePresenterListener;
        this.mealsRepository = mealsRepository;
    }


    @SuppressLint("CheckResult")
    public void getFavourites() {
        mealsRepository.getFavMeal().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Consumer<? super List<FavouriteMealsWithMeal>>) new Consumer<List<FavouriteMealsWithMeal>>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void accept(List<FavouriteMealsWithMeal> meals) throws Throwable {
                        favouritePresenterListener.addFavMealToAdapter(meals);
                    }
                }, throwable -> {

                });
    }

    public void deleteFromFavourite(FavouriteMealsWithMeal favouriteMealsWithMeal) {
        mealsRepository.deleteFavMeal(favouriteMealsWithMeal)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }


    public void loadRemoteData() {
        mealsRepository.restoreFavouriteMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
