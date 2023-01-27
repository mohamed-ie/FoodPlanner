package com.soha.foodplanner.ui.common.dialogs.confirm;

import androidx.annotation.NonNull;

import android.view.View;
import android.widget.Button;

import com.soha.foodplanner.R;
import com.soha.foodplanner.ui.common.dialogs.BaseDialogFragmentWithArgs;

import java.util.Collections;

public class ConfirmDialogFragment extends BaseDialogFragmentWithArgs<ConfirmDialogFragmentArgs> {
    private Button buttonYes;
    private Button buttonNo;
    public static final String YES = "yes";

    @Override
    public void initViews(@NonNull View view) {
        super.initViews(view);
        buttonNo = view.findViewById(R.id.motionLabelCancel);
        buttonYes = view.findViewById(R.id.buttonYes);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.dialog_fragment_confirm;
    }

    @Override
    public void setListeners() {
        buttonYes.setOnClickListener(v -> sendDataViaBackStackEntry(Collections.singletonMap(YES, true)));
        buttonNo.setOnClickListener(v -> navController.popBackStack());
    }

    @Override
    public ConfirmDialogFragmentArgs getSafeArgs() {
        return ConfirmDialogFragmentArgs.fromBundle(requireArguments());
    }

    @Override
    public void updateUiFromSafeArgs(ConfirmDialogFragmentArgs args) {
        textViewMessage.setText(args.getMessage());
    }
}