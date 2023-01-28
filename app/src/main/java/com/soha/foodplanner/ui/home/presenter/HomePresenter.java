package com.soha.foodplanner.ui.home.presenter;

import com.soha.foodplanner.data.dto.category.CategoryDto;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.mapper.MealMapper;
import com.soha.foodplanner.data.mapper.MealMapperImpl;
import com.soha.foodplanner.data.repository.meals.MealsRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter {
    private final HomePresenterListener homePresenterListener;
    private final MealsRepository repository;
    List<String> categoryItemList = new ArrayList<String>();


    public HomePresenter(HomePresenterListener homePresenterListener, MealsRepository repository) {
        this.homePresenterListener = homePresenterListener;
        this.repository = repository;
    }

    public void getMealsOfCategory(List<String> categoryItemList) {
        Disposable disposable = Flowable.fromIterable(categoryItemList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> repository.getMealsByCategory(s)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<List<MinMeal>>() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<MinMeal> minMeals) {
                                homePresenterListener.addMealToAdapter(minMeals, s);
                                //categoryAdapter.addNewCategory(minMeals, s);
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                            }
                        }));
    }

    public void getCategories() {
        repository.getAllCategories()
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

    public void insertToFav(long id) {
        repository.insertFavMeal(id)
                .subscribeOn(Schedulers.io()).subscribe();
    }

}
