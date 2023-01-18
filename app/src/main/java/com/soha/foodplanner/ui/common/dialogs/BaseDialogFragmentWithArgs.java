package com.soha.foodplanner.ui.common.dialogs;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavArgs;

public abstract class BaseDialogFragmentWithArgs<S extends NavArgs> extends BaseDialogFragment {
    protected S args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getSafeArgs();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (args != null)
            updateUiFromSafeArgs(args);
    }

    public abstract S getSafeArgs();

    public abstract void updateUiFromSafeArgs(S args);

}
