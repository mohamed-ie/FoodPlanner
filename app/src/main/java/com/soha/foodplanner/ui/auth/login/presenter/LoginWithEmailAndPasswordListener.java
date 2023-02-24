package com.soha.foodplanner.ui.auth.login.presenter;

import com.soha.foodplanner.ui.common.dialogs.listeners.ShowErrorDialog;
import com.soha.foodplanner.ui.common.dialogs.listeners.ShowRetryDialog;

public interface LoginWithEmailAndPasswordListener extends
        ShowErrorDialog,
        ShowRetryDialog,
        ShowSignupDialog {
    void onLoginLoading();

    void onLoginSuccess();
}
