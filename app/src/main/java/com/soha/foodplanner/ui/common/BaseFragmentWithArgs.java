package com.soha.foodplanner.ui.common;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavArgs;

import com.soha.foodplanner.ui.common.presenter.Presenter;

public abstract class BaseFragmentWithArgs<P extends Presenter, S extends NavArgs> extends BaseFragment<P> {
    protected S args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getSafeArgs();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public abstract S getSafeArgs();

}
