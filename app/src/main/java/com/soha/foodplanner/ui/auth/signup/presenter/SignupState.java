package com.soha.foodplanner.ui.auth.signup.presenter;

import com.soha.foodplanner.R;
import com.soha.foodplanner.ui.auth.login.presenter.LoginState;

public class SignupState extends LoginState {
    boolean nameError;
    boolean confirmPasswordError;
    int nameErrorMessage;
    int confirmPasswordErrorMessage;

    public SignupState() {
        nameErrorMessage = R.string.required;
        confirmPasswordErrorMessage =R.string.required;
    }

    public boolean isNameError() {
        return nameError;
    }

    public void setNameError(boolean nameError) {
        this.nameError = nameError;
    }

    public boolean isConfirmPasswordError() {
        return confirmPasswordError;
    }

    public void setConfirmPasswordError(boolean confirmPasswordError) {
        this.confirmPasswordError = confirmPasswordError;
    }

    public int getNameErrorMessage() {
        return nameErrorMessage;
    }

    public void setNameErrorMessage(int nameErrorMessage) {
        this.nameErrorMessage = nameErrorMessage;
    }

    public int getConfirmPasswordErrorMessage() {
        return confirmPasswordErrorMessage;
    }

    public void setConfirmPasswordErrorMessage(int confirmPasswordErrorMessage) {
        this.confirmPasswordErrorMessage = confirmPasswordErrorMessage;
    }
}
