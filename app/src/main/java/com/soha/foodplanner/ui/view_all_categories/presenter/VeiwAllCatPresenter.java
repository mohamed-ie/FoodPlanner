package com.soha.foodplanner.ui.view_all_categories.presenter;

import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.mapper.MealMapper;
import com.soha.foodplanner.data.mapper.MealMapperImpl;
import com.soha.foodplanner.data.repository.meals.MealsRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class VeiwAllCatPresenter {
    private final ViewAllCatListener viewAllCatListener;
    private final MealsRepository repository;

    public VeiwAllCatPresenter(ViewAllCatListener viewAllCatListener, MealsRepository repository) {
        this.viewAllCatListener = viewAllCatListener;
        this.repository = repository;
    }

    public void getMealsOfCategory(String categoryName) {
        repository.getMealsByCategory(categoryName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<MinMeal> minMeals) {
                        viewAllCatListener.addCategoryMealsToAdapter(minMeals);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    public void insertToFav(MinMeal minMeal) {
        repository.insertFavMeal(minMeal);
    }
}
