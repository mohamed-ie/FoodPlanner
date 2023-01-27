package com.soha.foodplanner.ui.profile.presenter;

import com.soha.foodplanner.ui.common.presenter.Presenter;
import com.soha.foodplanner.ui.profile.ProfileState;

public interface ProfilePresenter extends Presenter {
    void logout();

    ProfileState getState();
}
