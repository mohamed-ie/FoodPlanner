package com.soha.foodplanner.ui.common.dialogs.error;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.soha.foodplanner.R;
import com.soha.foodplanner.ui.common.dialogs.BaseDialogFragmentWithArgs;


public class ErrorDialogFragment extends BaseDialogFragmentWithArgs<ErrorDialogFragmentArgs> {
    protected Button buttonOk;
    protected TextView textViewMessage;

    @Override
    public int getLayoutResource() {
        return R.layout.dialog_fragment_error;
    }

    @Override
    public ErrorDialogFragmentArgs getSafeArgs() {
        return ErrorDialogFragmentArgs.fromBundle(requireArguments());
    }

    @Override
    public void updateUiFromSafeArgs(ErrorDialogFragmentArgs args) {
        textViewMessage.setText(args.getMessage());
    }

    @Override
    public void setListeners() {
        buttonOk.setOnClickListener(v -> navController.popBackStack());
    }

    @Override
    public void initViews(@NonNull View view) {
        textViewMessage=view.findViewById(R.id.textViewMessage);
        buttonOk = view.findViewById(R.id.buttonYes);
    }
}