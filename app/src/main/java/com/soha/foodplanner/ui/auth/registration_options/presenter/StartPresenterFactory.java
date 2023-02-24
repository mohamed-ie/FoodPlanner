package com.soha.foodplanner.ui.auth.registration_options.presenter;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.domain.data.repository.AuthRepository;

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
