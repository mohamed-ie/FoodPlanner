package com.soha.foodplanner.ui.main.profile.presenter;

import com.soha.foodplanner.ui.common.presenter.Presenter;
import com.soha.foodplanner.ui.main.profile.ProfileState;

public interface ProfilePresenter extends Presenter {
    void logout();

    ProfileState getState();

    void updateRememberMe();
}
