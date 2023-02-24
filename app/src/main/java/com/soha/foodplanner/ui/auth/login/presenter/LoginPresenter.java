package com.soha.foodplanner.ui.auth.login.presenter;

import com.soha.foodplanner.ui.common.presenter.DefaultPresenter;

public abstract class LoginPresenter extends DefaultPresenter {
    public abstract void login(String email, String password);
    public abstract boolean isInputsValid();
    public abstract LoginState getLoginState();
    public abstract void updateLoginState(String email, String password);
    public abstract void cancelLogin();
    public abstract void updateRememberMe(boolean rememberMe);
}
