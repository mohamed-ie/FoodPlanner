package com.soha.foodplanner.ui.home.presenter;

import android.content.Context;

import com.soha.foodplanner.data.data_source.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.data_source.remote.webservice.Webservice;
import com.soha.foodplanner.data.dto.category.CategoryDto;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.mapper.MealMapper;
import com.soha.foodplanner.data.mapper.MealMapperImpl;
import com.soha.foodplanner.data.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter {
    HomePresenterListener homePresenterListener;
    List<String> categoryItemList = new ArrayList<String>();
    Repository repo;
    TheMealDBWebService theMealDBWebService;


    public HomePresenter(HomePresenterListener homePresenterListener) {
        this.homePresenterListener = homePresenterListener;
        theMealDBWebService = Webservice.getInstance().getTheMealDBWebService();
    }

    public void getMealsOfCategory(List<String> categoryItemList) {


        MealMapper mapper = new MealMapperImpl();
        Disposable disposable = Flowable.fromIterable(categoryItemList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> theMealDBWebService.getMealsByCategory(s)
                        .subscribeOn(Schedulers.io())
                        .map(mapper::map)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<List<MinMeal>>() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<MinMeal> minMeals) {
                                homePresenterListener.addMealToAdapter(minMeals,s);
                                //categoryAdapter.addNewCategory(minMeals, s);
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                            }
                        }));
    }

    public void getCategories(){

        Single<CategoryDto> single = theMealDBWebService.getAllCategories();
        MealMapper mapper = new MealMapperImpl();
        single.subscribeOn(Schedulers.io())
                .map(mapper::map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<String>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<String> strings) {
                        categoryItemList = strings;
                        getMealsOfCategory(strings);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    public void insertToFav(Repository repo,MinMeal minMeal){

        repo.insertFavMeal(minMeal);
    }

}
