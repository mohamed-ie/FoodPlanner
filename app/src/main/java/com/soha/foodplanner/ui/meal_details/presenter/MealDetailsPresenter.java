package com.soha.foodplanner.ui.meal_details.presenter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import com.soha.foodplanner.data.data_source.remote.meals.MealsRemoteDataSourceImpl;
import com.soha.foodplanner.data.data_source.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.dto.meal.MealDto;
import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.mapper.MealMapper;
import com.soha.foodplanner.data.mapper.MealMapperImpl;
import com.soha.foodplanner.data.repository.meals.MealsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenter {
    private final MealsRepository repository;
    private CompleteMeal completeMeal;

    public MealDetailsPresenter(MealsRepository repository, MealDetailsListener mealDetailsListener) {
        this.repository = repository;
        this.mealDetailsListener = mealDetailsListener;
    }

    MealDetailsListener mealDetailsListener;

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
