package com.soha.foodplanner.ui.common.dialogs.login;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.soha.foodplanner.R;
import com.soha.foodplanner.ui.common.dialogs.BaseDialogFragmentWithArgs;

public class LoginDialogFragment extends BaseDialogFragmentWithArgs<LoginDialogFragmentArgs> {
    private Button buttonLogin;
    private Button buttonCancel;
    private TextView textViewMessage;

    @Override
    public int getLayoutResource() {
        return R.layout.dialog_fragment_login;
    }
    @Override
    public void setListeners() {
        buttonCancel.setOnClickListener(v -> navController.popBackStack());
        buttonLogin.setOnClickListener(v -> navController.navigate(LoginDialogFragmentDirections.actionLoginDialogFragmentToRegistrationOptions()));
    }
    @Override
    public LoginDialogFragmentArgs getSafeArgs() {
        return LoginDialogFragmentArgs.fromBundle(requireArguments());
    }
    @Override
    public void updateUiFromSafeArgs(LoginDialogFragmentArgs args) {
        textViewMessage.setText(args.getMessage());
    }
    @Override
    public void initViews(@NonNull View view) {
        buttonLogin = view.findViewById(R.id.buttonLogin);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        textViewMessage = view.findViewById(R.id.textViewMessage);
    }
}