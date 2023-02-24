package com.soha.foodplanner.ui.auth.signup.presenter;

import androidx.annotation.StringRes;

public interface OnFirebaseAuthComplete {
    void onSuccess();
    void onFailure(@StringRes int message);
}
