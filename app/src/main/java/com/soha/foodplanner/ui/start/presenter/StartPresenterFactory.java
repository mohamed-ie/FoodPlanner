package com.soha.foodplanner.ui.start.presenter;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.repository.auth.AuthRepository;

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
