package com.soha.foodplanner.ui.main.profile.presenter;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.domain.data.repository.AuthRepository;

public class ProfilePresenterFactory implements Factory<ProfilePresenter> {
    private final AuthRepository repository;
    private final ProfilePresenterListener listener;

    public ProfilePresenterFactory(AuthRepository repository, ProfilePresenterListener listener) {
        this.repository = repository;
        this.listener = listener;
    }

    @Override
    public ProfilePresenter create() {
        return new ProfilePresenterImpl(repository,listener);
    }
}
