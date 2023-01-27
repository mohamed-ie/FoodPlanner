package com.soha.foodplanner.ui.profile.presenter;

public interface LogoutListener {

    void onLogoutLoading();
    void onLogoutSuccess();
    void onLogoutError(String message);
}
