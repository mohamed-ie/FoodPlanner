package com.soha.foodplanner.ui.view_all_categories.presenter;

import com.soha.foodplanner.data.data_source.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.mapper.MealMapper;
import com.soha.foodplanner.data.mapper.MealMapperImpl;
import com.soha.foodplanner.data.repository.Repository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class VeiwAllCatPresenter {
    ViewAllCatListener viewAllCatListener;
    Repository repo;
    TheMealDBWebService theMealDBWebService;

    public VeiwAllCatPresenter(ViewAllCatListener viewAllCatListener, TheMealDBWebService theMealDBWebService,Repository repo) {
        this.viewAllCatListener = viewAllCatListener;
        this.theMealDBWebService = theMealDBWebService;
        this.repo=repo;
    }

    public void getMealsOfCategory(String categoryName){
        MealMapper mapper = new MealMapperImpl();
        theMealDBWebService.getMealsByCategory(categoryName)
                .subscribeOn(Schedulers.io())
                .map(mapper::map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MinMeal>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<MinMeal> minMeals) {
                        viewAllCatListener.addCategoryMealsToAdapter(minMeals);
                        //categoryAdapter.addNewCategory(minMeals, s);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });


    }
    public void insertToFav(MinMeal minMeal){
        repo.insertFavMeal(minMeal);
    }
}
