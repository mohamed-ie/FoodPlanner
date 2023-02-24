package com.soha.foodplanner.ui.common.dialogs.retry;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.soha.foodplanner.R;
import com.soha.foodplanner.ui.common.dialogs.BaseDialogFragmentWithArgs;

import java.util.Collections;

public class RetryDialogFragment extends BaseDialogFragmentWithArgs<RetryDialogFragmentArgs> {
    private Button buttonCancel;
    private TextView textViewMessage;
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
        textViewMessage=view.findViewById(R.id.textViewMessage);
        buttonRetry = view.findViewById(R.id.buttonYes);
        buttonCancel = view.findViewById(R.id.motionLabelCancel);
    }

    @Override
    public void setListeners() {
        buttonRetry.setOnClickListener(v -> sendDataViaBackStackEntry(Collections.singletonMap(RETRY, true)));
        buttonCancel.setOnClickListener(v -> navController.popBackStack());
    }

    @Override
    public void updateUiFromSafeArgs(RetryDialogFragmentArgs args) {
        textViewMessage.setText(args.getMessage());
    }
}