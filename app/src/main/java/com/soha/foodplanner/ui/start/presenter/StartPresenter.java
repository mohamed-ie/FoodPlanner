package com.soha.foodplanner.ui.start.presenter;

import com.google.firebase.auth.AuthCredential;
import com.soha.foodplanner.ui.common.presenter.Presenter;

public interface StartPresenter extends Presenter {
    void login(String idToken);
}
