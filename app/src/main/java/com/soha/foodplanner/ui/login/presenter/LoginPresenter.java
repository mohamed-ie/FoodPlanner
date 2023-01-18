package com.soha.foodplanner.ui.login.presenter;

import com.soha.foodplanner.ui.common.presenter.Presenter;

public interface LoginPresenter extends Presenter {
    String TAG = "LoginPresenter";
    void login(String email, String password);

    boolean isInputsValid();

    LoginState getLoginState();

    void updateLoginState(String email, String password);
}
