package com.soha.foodplanner.ui.auth.signup.presenter;

import com.soha.foodplanner.ui.common.presenter.DefaultPresenter;

public abstract class SignUpPresenter extends DefaultPresenter {
    public abstract void signUp(String name, String email, String password);

    public abstract void showSignupDialog(Throwable throwable);

    public abstract void updateLoginState(String name, String email, String password, String confirmPassword);

    public abstract SignupState getState();

    public abstract boolean isInputsValid();
}
