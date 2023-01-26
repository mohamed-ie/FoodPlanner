package com.soha.foodplanner.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.Observer;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.soha.foodplanner.MyApp;
import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.ui.common.BaseFragment;
import com.soha.foodplanner.ui.common.dialogs.loading.LoadingDialogFragment;
import com.soha.foodplanner.ui.common.dialogs.retry.RetryDialogFragment;
import com.soha.foodplanner.ui.login.presenter.LoginPresenter;
import com.soha.foodplanner.ui.login.presenter.LoginPresenterFactory;
import com.soha.foodplanner.ui.login.presenter.LoginPresenterListener;
import com.soha.foodplanner.ui.login.presenter.LoginState;

public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginPresenterListener {
    private CheckBox checkBoxRememberMe;
    private Button buttonLogin;
    private ImageButton imageButtonBack;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextEmail;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_login;
    }

    @Override
    protected Factory<LoginPresenter> getPresenterFactory() {
        return new LoginPresenterFactory(((MyApp) requireActivity().getApplication()).getAuthRepository(), this);
    }

    //fragment lifecycle
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDialogCancel(boolean cancel) {
        if (cancel)
            presenter.cancelLogin();
    }

    @Override
    protected void onDialogRetry(boolean retry) {
        if (retry) {
            login();
        }
    }

    @Override
    public void initDependencies() {
        super.initDependencies();
    }


    //    @SuppressLint("NonConstantResourceId")
    @SuppressLint("NonConstantResourceId")
    private void showErrorDialog(int messageResource) {
        switch (messageResource) {
//            case R.string.email_or_password_incorrect:
//            case R.string.no_user_with_this_email:
//                navController.navigate(LoginFragmentDirections.actionLoginFragmentToErrorDialogFragment(messageResource));
//                break;
            default:
                navController.navigate(LoginFragmentDirections.actionLoginFragmentToRetryDialogFragment(messageResource));
        }
    }

    @Override
    protected void setListeners() {
        buttonLogin.setOnClickListener(v -> login());
        imageButtonBack.setOnClickListener(v -> navController.popBackStack());
    }


    @Override
    protected void initViews(@NonNull View view) {
        textInputEditTextEmail = view.findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = view.findViewById(R.id.textInputEditTextPassword);
        textInputLayoutEmail = view.findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = view.findViewById(R.id.textInputLayoutPassword);
        //
        buttonLogin = view.findViewById(R.id.buttonLogin);
        //
        imageButtonBack = view.findViewById(R.id.imageButtonBack);
        //
        checkBoxRememberMe = view.findViewById(R.id.checkBoxRememberMe);
    }


    private void login() {
        String email = textInputEditTextEmail.getText().toString();
        String password = textInputEditTextPassword.getText().toString();
        presenter.updateLoginState(email, password);
        if (presenter.isInputsValid()) {
            presenter.login(email, password);
            clearEmailError();
            clearPasswordError();
        } else showFieldsErrors();
    }

    private void showFieldsErrors() {
        LoginState state = presenter.getLoginState();
        if (state.isEmailError())
            showEmailError(state.getEmailErrorMessage());
        else clearEmailError();

        if (state.isPasswordError())
            showPasswordError(state.getPasswordErrorMessage());
        else clearPasswordError();
    }

    private void showEmailError(@StringRes int message) {
        textInputLayoutEmail.setError(getText(message));
        textInputLayoutEmail.setErrorEnabled(true);
    }

    private void clearEmailError() {
        textInputLayoutEmail.setErrorEnabled(false);
    }

    private void showPasswordError(@StringRes int message) {
        textInputLayoutPassword.setError(getText(message));
        textInputLayoutPassword.setErrorEnabled(true);
    }

    private void clearPasswordError() {
        textInputLayoutPassword.setErrorEnabled(false);
    }

    @Override
    public void loginWithEmailAndPasswordLoading() {
        navController.navigate(LoginFragmentDirections.actionLoginFragmentToLoadingFragment());
    }

    @Override
    public void loginWithEmailAndPasswordSuccess() {
        navController.popBackStack();
        navController.navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment());
        Toast.makeText(requireContext(), R.string.login_successful, Toast.LENGTH_SHORT).show();
        presenter.updateRememberMe(checkBoxRememberMe.isChecked());
    }

    @Override
    public void loginWithEmailAndPasswordError(String message) {
        navController.popBackStack();
        showErrorDialog(R.string.unexpected_error_try_again);
    }
}