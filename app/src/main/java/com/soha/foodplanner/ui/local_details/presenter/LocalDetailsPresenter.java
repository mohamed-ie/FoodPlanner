package com.soha.foodplanner.ui.local_details.presenter;

import com.soha.foodplanner.data.local.entities.Ingredient;
import com.soha.foodplanner.data.local.entities.IngredientWithMeal;
import com.soha.foodplanner.data.local.entities.Meal;
import com.soha.foodplanner.data.repository.meals.MealsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LocalDetailsPresenter {

    private final LocalDetailsListener localDetailsListener;
    private final MealsRepository repository;

    public LocalDetailsPresenter(LocalDetailsListener localDetailsListener, MealsRepository repository) {
        this.localDetailsListener = localDetailsListener;
        this.repository = repository;
    }

    public void getLocalDetails(long mealIdStr) {
        repository.selectMealById(mealIdStr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull IngredientWithMeal meal) {
                        localDetailsListener.setLocalValues(meal);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }
}
