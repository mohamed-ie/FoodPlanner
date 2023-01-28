package com.soha.foodplanner.ui.start.presenter;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.repository.auth.AuthRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class StartPresenterFactory implements Factory<StartPresenter> {

    private final AuthRepository repository;
    private final StartPresenterListener listener;

    public StartPresenterFactory(AuthRepository repository, StartPresenterListener listener) {
        this.repository = repository;
        this.listener = listener;
    }


    @Override
    public StartPresenter create() {
        return new StartPresenterImpl(repository, listener);
    }

}
