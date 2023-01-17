package com.soha.foodplanner.ui.common.dialogs.error;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;

import com.soha.foodplanner.R;
import com.soha.foodplanner.ui.common.dialogs.BaseDialogFragment;


public class ErrorDialogFragment extends BaseDialogFragment<ErrorDialogFragmentArgs> {
    protected TextView textViewMessage;
    protected Button buttonOk;
    protected NavController navController;

    @Override
    public int getLayoutResource() {
        return R.layout.dialog_fragment_error;
    }

    @Override
    public ErrorDialogFragmentArgs getSafeArgs() {
        return ErrorDialogFragmentArgs.fromBundle(getArguments());
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
    public void initViews(View view) {
        textViewMessage = view.findViewById(R.id.textViewMessage);
        buttonOk = view.findViewById(R.id.buttonRetry);
    }
}