package com.soha.foodplanner.ui.login.presenter;

import androidx.annotation.NonNull;

import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.data.repository.login.LoginRepository;

public class LoginPresenterImpl implements LoginPresenter {
    private final LoginState state = new LoginState();
    private final LoginRepository repository;

    public LoginPresenterImpl(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public void login(String email, String password) {
        repository.login(email, password);
    }

    public boolean isInputsValid() {
        return !state.isEmailError() && !state.isPasswordError();
    }

    @Override
    public LoginState getLoginState() {
        return state;
    }

    @Override
    public void updateLoginState(String email, String password) {
        if (email.isEmpty()) {
            state.setEmailError(true);
            state.setEmailErrorMessage(R.string.required);
        } else if (isEmailInForm(email)) {
            state.setEmailErrorMessage(R.string.not_formatted_email);
            state.setEmailError(true);
        } else {
            state.setEmailError(false);
        }

        if (password.isEmpty()) {
            state.setPasswordError(true);
            state.setPasswordErrorMessage(R.string.required);
        } else {
            state.setPasswordError(false);
        }
    }

    private boolean isEmailInForm(@NonNull String email) {
        return !email.matches(Constants.PATTERN_EMAIL);
    }

}
