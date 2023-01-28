package com.soha.foodplanner.ui.meal_details.presenter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import com.soha.foodplanner.data.data_source.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.dto.meal.MealDto;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenter {
    public MealDetailsPresenter(TheMealDBWebService theMealDBWebService, MealDetailsListener mealDetailsListener,View view) {
        this.theMealDBWebService = theMealDBWebService;
        this.mealDetailsListener = mealDetailsListener;
        this.view=view;
    }

    TheMealDBWebService theMealDBWebService;


    View view;
    MealDetailsListener mealDetailsListener;
    @SuppressLint("CheckResult")
    public void getDetails(String mealIdStr){
        Single<MealDto> single = theMealDBWebService.getMealDetailsById(Long.parseLong(mealIdStr));
        single
                .subscribeOn(Schedulers.io())
                .map(t->t.getMeals().get(0))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(e-> Log.e("error",e.getMessage()))
                .subscribe(e->
                        {   e.getIngredients();
                            //setMealValues(e,view);
                            mealDetailsListener.setValues(e,view);
                        }
                );

    }
}
