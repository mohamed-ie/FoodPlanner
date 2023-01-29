package com.soha.foodplanner.ui.planned;

import android.util.Log;

import com.soha.foodplanner.data.local.entities.PlanedMealWithMeal;
import com.soha.foodplanner.data.local.entities.PlannedMealWithMealAndIngredients;
import com.soha.foodplanner.data.local.entities.PlannedMeals;
import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.repository.meals.MealsRepository;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlannedPresenterImpl implements PlannedPresenter {
    private final MealsRepository repository;
    private final PlannedPresenterListener listener;

    private final List<WeekPlan> weekPlans = new ArrayList<>();

    public PlannedPresenterImpl(MealsRepository repository, PlannedPresenterListener listener) {
        this.listener = listener;
        this.repository = repository;
    }

    @Override
    public void loadPlannedMeals() {
        repository.getPlannedMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<>() {
                    Subscription subscription;
                    long count = 1;
                    int start = 0;
                    int current = 0;

                    @Override
                    public void onSubscribe(Subscription s) {
                        subscription = s;
                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(List<PlanedMealWithMeal> planedMealWithMeal) {
                        listener.onNextItem(planedMealWithMeal);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void remove(long id) {

    }

}
