package com.soha.foodplanner.ui.auth.signup.presenter;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.domain.data.repository.AuthRepository;

public class SignupPresenterFactory implements Factory<SignUpPresenter> {
    private final AuthRepository repository;
    private final SignUpPresenterListener listener;

    public SignupPresenterFactory(AuthRepository repository, SignUpPresenterListener listener) {
        this.repository = repository;
        this.listener = listener;
    }

    @Override
    public SignUpPresenter create() {
        return new SignUpPresenterImpl(repository, listener);
    }
}
