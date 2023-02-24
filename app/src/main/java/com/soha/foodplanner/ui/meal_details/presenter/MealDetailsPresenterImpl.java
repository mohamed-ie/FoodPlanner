package com.soha.foodplanner.ui.meal_details.presenter;

import android.annotation.SuppressLint;

import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.repository.meals.MealsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenterImpl extends MealDetailsPresenter {
    private final MealsRepository repository;
    private CompleteMeal completeMeal;

    public MealDetailsPresenterImpl(MealsRepository repository) {
        this.repository = repository;
    }

    MealDetailsPresenterListener mealDetailsListener;

    @SuppressLint("CheckResult")
    public void getDetails(long mealIdStr) {
        repository.getMealById(mealIdStr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(e ->
                        {
                            completeMeal = e;
                            //setMealValues(e,view);
                            mealDetailsListener.setValues(e);
//                            mealDetailsListener.setIngredients(e.getIngredients(), view);
                        }
                ,throwable -> {});

    }

    public void addToPlanned(long time, String mealTime) {
        repository.insertPlanMeal(completeMeal, time, mealTime)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
