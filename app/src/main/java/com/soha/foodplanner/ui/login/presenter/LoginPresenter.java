package com.soha.foodplanner.ui.login.presenter;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface LoginPresenter {
    void login(String email, String password);
}
