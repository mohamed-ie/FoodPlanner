package com.soha.foodplanner.data.remote.home;

import android.util.Log;

import com.soha.foodplanner.data.mapper.Mapper;
import com.soha.foodplanner.data.model.CompleteMeal;
import com.soha.foodplanner.data.remote.dto.meal.RandomMealDto;
import com.soha.foodplanner.data.remote.webservice.TheMealDBWebService;

import org.reactivestreams.Subscription;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeRemoteDataSourceImpl implements HomeRemoteDataSource {
    private final TheMealDBWebService webService;
    private final Mapper<RandomMealDto, CompleteMeal> mapper;
    private final HomeRemoteDataSourceListener homeRemoteDataSourceListener;

    public HomeRemoteDataSourceImpl(TheMealDBWebService webService,
                                    Mapper<RandomMealDto, CompleteMeal> mapper,
                                    HomeRemoteDataSourceListener homeRemoteDataSourceListener
    ) {
        this.webService = webService;
        this.mapper = mapper;
        this.homeRemoteDataSourceListener = homeRemoteDataSourceListener;
    }

    @Override
    public void getRandomMeals(int count) {
        final int[] currentCount = {0};
        webService
                .getRandomMeal()
                .subscribeOn(Schedulers.io())
                .repeat(count)
                .map(mapper::map)
//                .repeatUntil(() -> currentCount[0] == count)
//                .distinct()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<CompleteMeal>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        Log.e("TAG", "onSubscribe: ");
                    }

                    @Override
                    public void onNext(CompleteMeal completeMeal) {
                        Log.e("TAG", "onSubscribe: ");

                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("TAG", "onSubscribe: ");

                    }

                    @Override
                    public void onComplete() {
                        Log.e("TAG", "onSubscribe: ");

                    }
                });
    }
}
