package com.soha.foodplanner.ui.login.presenter;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.repository.auth.AuthRepository;

public class LoginPresenterFactory implements Factory<LoginPresenter> {

    private final AuthRepository repository;
    private final LoginPresenterListener listener;
    public LoginPresenterFactory(AuthRepository repository, LoginPresenterListener listener) {
        this.repository = repository;
        this.listener = listener;
    }

    @Override
    public LoginPresenter create() {
       return new LoginPresenterImpl(repository,listener);
    }
}
