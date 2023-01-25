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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RetryDialogFragment extends BaseDialogFragmentWithArgs<RetryDialogFragmentArgs> {
    private Button buttonCancel;
    public static final String RETRY = "retry";
    private Button buttonRetry;

    @Override
    public int getLayoutResource() {
        return R.layout.dialog_fragment_retry;
    }

    @Override
    public RetryDialogFragmentArgs getSafeArgs() {
        return RetryDialogFragmentArgs.fromBundle(requireArguments());
    }

    @Override
    public void initViews(@NonNull View view) {
        super.initViews(view);
        buttonRetry = view.findViewById(R.id.buttonRetry);
        buttonCancel = view.findViewById(R.id.buttonCancel);
    }

    @Override
    public void setListeners() {
        buttonRetry.setOnClickListener(v -> {
            sendDataViaBackStackEntry(Collections.singletonMap(RETRY, true));
        });
        buttonCancel.setOnClickListener(v -> navController.popBackStack());
    }

    @Override
    public void updateUiFromSafeArgs(RetryDialogFragmentArgs args) {
        textViewMessage.setText(args.getMessage());
    }
}