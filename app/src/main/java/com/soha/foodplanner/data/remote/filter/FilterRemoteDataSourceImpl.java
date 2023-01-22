package com.soha.foodplanner.data.remote.filter;

import com.soha.foodplanner.data.mapper.Mapper;
import com.soha.foodplanner.data.model.MinMeal;
import com.soha.foodplanner.data.remote.dto.min_meal.MinMealDto;
import com.soha.foodplanner.data.remote.webservice.TheMealDBWebService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FilterRemoteDataSourceImpl implements FilterRemoteDataSource {
    private final TheMealDBWebService theMealDBWebService;
    private final Mapper<MinMealDto, List<MinMeal>> mapper;
    private final FilterRemoteDataSourceListener listener;

    private final List<Disposable> disposables = new ArrayList<>();

    public FilterRemoteDataSourceImpl(TheMealDBWebService theMealDBWebService, Mapper<MinMealDto, List<MinMeal>> mapper, FilterRemoteDataSourceListener listener) {
        this.theMealDBWebService = theMealDBWebService;
        this.mapper = mapper;
        this.listener = listener;
    }

    @Override
    public void closeAll() {
        for (Disposable disposable : disposables)
            disposable.dispose();
    }

    @Override
    public void getAreaMeals(String area) {
        theMealDBWebService
                .getAreaMeals(area)
                .subscribeOn(Schedulers.io())
                .map(mapper::map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposables.add(d);
                        listener.onLoading();
                    }

                    @Override
                    public void onSuccess(@NonNull List<MinMeal> meals) {
                        listener.onSuccess(meals);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onFailed(e.getMessage());
                    }
                });
    }
}
