package com.soha.foodplanner.ui.common.dialogs.retry;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.navigation.NavBackStackEntry;

import com.soha.foodplanner.R;
import com.soha.foodplanner.ui.common.dialogs.BaseDialogFragmentWithArgs;
import com.soha.foodplanner.ui.login.LoginFragment;

import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

public class RetryDialogFragment extends BaseDialogFragmentWithArgs<RetryDialogFragmentArgs> {
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
    public void initViews(@NonNull View view) {
        super.initViews(view);
        buttonRetry = view.findViewById(R.id.buttonRetry);
        buttonCancel = view.findViewById(R.id.buttonCancel);
    }

    @Override
    public void setListeners() {
        buttonRetry.setOnClickListener(v -> retry());
        buttonCancel.setOnClickListener(v -> navController.popBackStack());
    }

    @NonNull
    @Contract(pure = true)
    private LifecycleEventObserver createNavBackStackEntryObserver(NavBackStackEntry navBackStackEntry, Map<String, ?> data) {
        return (LifecycleEventObserver) (source, event) -> {
            if (event.equals(Lifecycle.Event.ON_RESUME) && navBackStackEntry.getSavedStateHandle().contains(LoginFragment.RETRY)) {
                for (String key : data.keySet())
                    navBackStackEntry.getSavedStateHandle().set(key, data.get(key));
            }
        };
    }

    private void retry() {
//        NavBackStackEntry navBackStackEntry = navController.getBackStackEntry(R.id.loginFragment);
        NavBackStackEntry navBackStackEntry = navController.getPreviousBackStackEntry();
        //data to be sent via backstack entry
        Map<String, Boolean> data = new HashMap<>();
        data.put(LoginFragment.RETRY, true);
        //create observer
        LifecycleEventObserver observer = createNavBackStackEntryObserver(navBackStackEntry, data);
        //register observer
        navBackStackEntry.getLifecycle().addObserver(observer);
        //remove observer
        removeNavStackEntryObserver(navBackStackEntry, observer);
        navController.popBackStack();
    }

    private void removeNavStackEntryObserver(NavBackStackEntry navBackStackEntry, LifecycleEventObserver observer) {
        getViewLifecycleOwner().getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event.equals(Lifecycle.Event.ON_DESTROY))
                navBackStackEntry.getLifecycle().removeObserver(observer);
        });
    }

    @Override
    public void updateUiFromSafeArgs(RetryDialogFragmentArgs args) {
        textViewMessage.setText(args.getMessage());
    }
}