package com.soha.foodplanner.ui.filter.presenter;


import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.repository.meals.MealsRepository;
import com.soha.foodplanner.ui.filter.MealsFilterState;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsFilterPresenterImpl implements MealsFilterPresenter {
    private final MealsRepository repository;
    private final MealsFilterPresenterListener listener;
    private final MealsFilterState state = new MealsFilterState();
    private Disposable disposable;

    public MealsFilterPresenterImpl(MealsRepository repository, MealsFilterPresenterListener listener) {
        this.repository = repository;
        this.listener = listener;
    }


    @Override
    public void loadByCategory(String category) {
        repository.getMealsByCategory(category)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MealsByCategoryObserver());
    }

    @Override
    public void loadByArea(String area) {
        repository.getMealsByArea(area)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MealsByCategoryObserver());
    }

    @Override
    public void searchByName(CharSequence name) {
        Flowable.fromIterable(state.getMinMeals())
                .subscribeOn(Schedulers.computation())
                .filter(meal -> meal.getName().toLowerCase().startsWith(name.toString().toLowerCase()))
                .toList()
                .doOnSubscribe(d -> disposable = d)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MealsByCategoryObserver());
    }

    private class MealsByCategoryObserver implements SingleObserver<List<MinMeal>> {

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            listener.onGetMealsByCategoryLoading();
        }

        @Override
        public void onSuccess(@NonNull List<MinMeal> meals) {
            if (state.getMinMeals() == null)
                state.setMinMeals(meals);
            listener.onGetMealsByCategorySuccess(meals);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            listener.onGetMealsByCategoryError(e.getMessage());
        }
    }

    @Override
    public void destroy() {
        MealsFilterPresenter.super.destroy();
        if (disposable != null)
            disposable.dispose();
    }
}

