package com.soha.foodplanner.ui.start.presenter;

public interface LoginWithGoogleListener {
    void onLoginWithGoogleLoading();

    void onLoginWithGoogleSuccess();

    void onLoginWithGoogleError(String message);
}
