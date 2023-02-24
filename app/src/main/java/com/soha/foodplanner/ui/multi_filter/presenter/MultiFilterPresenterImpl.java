package com.soha.foodplanner.ui.multi_filter.presenter;

import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.repository.meals.MealsRepository;
import com.soha.foodplanner.ui.multi_filter.MultiFilterState;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MultiFilterPresenterImpl extends MultiFilterPresenter {
    private final MealsRepository repository;
    private final MultiFilterPresenterListener listener;
    private final MultiFilterState state = new MultiFilterState();

    public MultiFilterPresenterImpl(MealsRepository repository, MultiFilterPresenterListener listener) {
        this.repository = repository;
        this.listener = listener;
    }

    @Override
    public void getAllMeals() {
        disposables.add(repository.getAllCompleteMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completeMealIntegerPair -> {
                            CompleteMeal completeMeal = completeMealIntegerPair.first;
                            listener.onNextMeal(completeMeal, completeMealIntegerPair.second);
                            state.getMeals().add(completeMeal);
                        },
                        throwable -> {},
                        listener::onLoadAllMealsComplete));
    }

    @Override
    public void searchByName(CharSequence name) {
        listener.clearList();
        disposables.add(Flowable.fromIterable(state.getMeals())
                .subscribeOn(Schedulers.computation())
                .filter(meal -> meal.getMeal().getName().toLowerCase().startsWith(name.toString().toLowerCase()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onSearchNextMeal));
    }

}
