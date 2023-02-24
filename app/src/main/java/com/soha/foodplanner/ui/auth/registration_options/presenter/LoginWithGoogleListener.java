package com.soha.foodplanner.ui.auth.registration_options.presenter;

public interface LoginWithGoogleListener {
    void onLoginWithGoogleLoading();

    void onLoginWithGoogleSuccess();

    void onLoginWithGoogleError(String message);
}
