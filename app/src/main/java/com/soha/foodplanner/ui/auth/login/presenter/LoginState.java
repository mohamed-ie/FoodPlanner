package com.soha.foodplanner.ui.auth.login.presenter;

import androidx.annotation.StringRes;

import com.soha.foodplanner.R;

public class LoginState {
    boolean emailError;
    boolean passwordError;
    int emailErrorMessage;
    int passwordErrorMessage;

    public LoginState() {
        emailErrorMessage = R.string.required;
        passwordErrorMessage =R.string.required;
    }

    public int getEmailErrorMessage() {
        return emailErrorMessage;
    }

    public void setEmailErrorMessage(@StringRes int emailErrorMessage) {
        this.emailErrorMessage = emailErrorMessage;
    }

    public int getPasswordErrorMessage() {
        return passwordErrorMessage;
    }

    public void setPasswordErrorMessage(@StringRes int passwordErrorMessage) {
        this.passwordErrorMessage = passwordErrorMessage;
    }

    public boolean isEmailError() {
        return emailError;
    }

    public void setEmailError(boolean emailError) {
        this.emailError = emailError;
    }

    public boolean isPasswordError() {
        return passwordError;
    }

    public void setPasswordError(boolean passwordError) {
        this.passwordError = passwordError;
    }
}
