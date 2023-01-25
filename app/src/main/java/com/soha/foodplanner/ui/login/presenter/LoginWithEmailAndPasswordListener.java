package com.soha.foodplanner.ui.login.presenter;

public interface LoginWithEmailAndPasswordListener {
    void loginWithEmailAndPasswordLoading();
    void loginWithEmailAndPasswordSuccess();
    void loginWithEmailAndPasswordError(String message);
}
