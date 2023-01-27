package com.soha.foodplanner.ui.multi_filter.presenter;

import android.util.Pair;

import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.repository.meals.MealsRepository;
import com.soha.foodplanner.ui.filter.presenter.MealsFilterPresenterImpl;
import com.soha.foodplanner.ui.multi_filter.MultiFilterState;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MultiFilterPresenterImpl implements MultiFilterPresenter {
    private final MealsRepository repository;
    private final MultiFilterPresenterListener listener;
    private final MultiFilterState state = new MultiFilterState();

    private Disposable disposable;

    public MultiFilterPresenterImpl(MealsRepository repository, MultiFilterPresenterListener listener) {
        this.repository = repository;
        this.listener = listener;
    }

    @Override
    public void getAllMeals() {
        repository.getAllCompleteMeals()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new GetAllMealsObserver());
    }

    private class GetAllMealsObserver implements FlowableSubscriber<Pair<CompleteMeal, Integer>> {
        private Subscription subscription;
        private int progress = 0;

        @Override
        public void onSubscribe(@NonNull Subscription s) {
            s.request(1);
            subscription = s;
        }

        @Override
        public void onNext(Pair<CompleteMeal, Integer> completeMealFloatPair) {
            CompleteMeal completeMeal = completeMealFloatPair.first;
            progress = completeMealFloatPair.second;
            listener.onNextMeal(completeMeal, progress);
            state.getMeals().add(completeMeal);
            subscription.request(state.getMeals().size());
        }


        @Override
        public void onError(Throwable t) {
            listener.onLoadAllMealsError(t.getMessage(), progress);
        }

        @Override
        public void onComplete() {
            listener.onLoadAllMealsComplete();
        }
    }

    @Override
    public void searchByName(CharSequence name) {
        Flowable.fromIterable(state.getMeals())
                .subscribeOn(Schedulers.computation())
                .filter(meal -> meal.getMeal().getName().toLowerCase().startsWith(name.toString().toLowerCase()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CompleteMeal>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                      s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(CompleteMeal completeMeal) {
                        listener.onNextMeal(completeMeal, 100);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
