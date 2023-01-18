package com.soha.foodplanner.ui.start.presenter;

import com.google.firebase.auth.AuthCredential;
import com.soha.foodplanner.data.repository.start.StartRepository;
import com.soha.foodplanner.ui.common.presenter.Presenter;

public class StartPresenterImpl implements StartPresenter {
    private final StartRepository repository;

    public StartPresenterImpl(StartRepository repository) {
        this.repository = repository;
    }

    @Override
    public void login(String idToken) {
        repository.login(idToken);
    }
}
