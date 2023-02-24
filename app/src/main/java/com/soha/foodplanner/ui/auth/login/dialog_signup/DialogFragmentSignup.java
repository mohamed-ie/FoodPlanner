package com.soha.foodplanner.ui.auth.login.dialog_signup;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.soha.foodplanner.R;
import com.soha.foodplanner.ui.common.dialogs.BaseDialogFragment;

public class DialogFragmentSignup extends BaseDialogFragment {

    private Button buttonSignup;
    private Button buttonCancel;

    @Override
    public int getLayoutResource() {
        return R.layout.dialog_fragment_signup;
    }

    @Override
    public void initViews(@NonNull View view) {
        buttonSignup = view.findViewById(R.id.buttonSignup);
        buttonCancel = view.findViewById(R.id.buttonCancel);
    }

    @Override
    public void setListeners() {
        buttonSignup.setOnClickListener(v -> navController.navigate(DialogFragmentSignupDirections.actionDialogFragmentSignupToSignUpFragment2()));
        buttonCancel.setOnClickListener(v -> navController.popBackStack());
    }
}