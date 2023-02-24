package com.soha.foodplanner.ui.main.home.presenter;

import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.ui.common.presenter.PresenterListener;

public interface HomePresenterListener extends PresenterListener {
    void onNextInspiration(CompleteMeal completeMeal);
}
