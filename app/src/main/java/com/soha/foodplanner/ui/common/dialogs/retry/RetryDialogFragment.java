package com.soha.foodplanner.ui.common.dialogs.retry;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavBackStackEntry;

import com.soha.foodplanner.R;
import com.soha.foodplanner.ui.common.dialogs.BaseDialogFragment;
import com.soha.foodplanner.ui.login.LoginFragment;

public class RetryDialogFragment extends BaseDialogFragment<RetryDialogFragmentArgs> {
    private Button buttonCancel;
    private Button buttonRetry;

    @Override
    public int getLayoutResource() {
        return R.layout.dialog_fragment_retry;
    }

    @Override
    public RetryDialogFragmentArgs getSafeArgs() {
        return RetryDialogFragmentArgs.fromBundle(getArguments());
    }

    @Override
    public void initViews(View view) {
        super.initViews(view);
        buttonRetry = view.findViewById(R.id.buttonRetry);
        buttonCancel = view.findViewById(R.id.buttonCancel);
    }

    @Override
    public void setListeners() {
        buttonRetry.setOnClickListener(v -> {
            NavBackStackEntry navBackStackEntry = navController.getBackStackEntry(R.id.loginFragment);
            final LifecycleObserver observer = new LifecycleEventObserver() {
                @Override
                public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                    if (event.equals(Lifecycle.Event.ON_RESUME) && navBackStackEntry.getSavedStateHandle().contains(LoginFragment.RETRY)) {
                        navBackStackEntry.getSavedStateHandle().set(LoginFragment.RETRY, true);
                        // Do something with the result
                    }
                }

            };
            navBackStackEntry.getLifecycle().addObserver(observer);
            getViewLifecycleOwner().getLifecycle().addObserver(new LifecycleEventObserver() {
                @Override
                public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                    if (event.equals(Lifecycle.Event.ON_DESTROY)) {
                        navBackStackEntry.getSavedStateHandle().set(LoginFragment.RETRY, true);
                        navBackStackEntry.getLifecycle().removeObserver(observer);
                    }
                }
            });
            navController.popBackStack();
        });

        buttonCancel.setOnClickListener(v -> navController.popBackStack());
    }

    @Override
    public void updateUiFromSafeArgs(RetryDialogFragmentArgs args) {
        textViewMessage.setText(args.getMessage());
    }
}