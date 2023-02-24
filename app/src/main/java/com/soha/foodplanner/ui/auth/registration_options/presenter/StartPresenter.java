package com.soha.foodplanner.ui.auth.registration_options.presenter;

import com.soha.foodplanner.ui.common.presenter.DefaultPresenter;

public abstract class StartPresenter extends DefaultPresenter {
    public abstract void login(String idToken);
    public abstract void isLoggedIn();
    public abstract void updateRememberMe();
}
