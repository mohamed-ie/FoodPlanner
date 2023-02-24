package com.soha.foodplanner.ui.main.home.presenter;

import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.repository.meals.MealsRepository;
import com.soha.foodplanner.ui.main.home.HomeState;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;

public class HomePresenterImpl extends HomePresenter<HomePresenterListener> {
    private final MealsRepository repository;
    private final HomeState state = new HomeState();


    public HomePresenterImpl(MealsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void insertToFavourite(long id) {
        repository.insertFavMeal(id).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void loadInspirations() {
        if (state.getInspirations().isEmpty())
            disposables.add(repository.getInspiration(Constants.INSPIRATION_COUNT)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new LoadInspirationsObserver()));
        else state.getInspirations().forEach(listener::onNextInspiration);

    }

    private class LoadInspirationsObserver extends DisposableSubscriber<CompleteMeal> {
        @Override
        protected void onStart() {
            request(1);
        }

        @Override
        public void onNext(CompleteMeal completeMeal) {
            listener.onNextInspiration(completeMeal);
            state.insertInspiration(completeMeal);
            request(state.getInspirations().size());
        }

        @Override
        public void onError(Throwable t) {
            t.printStackTrace();
        }

        @Override
        public void onComplete() {

        }
    }
}
