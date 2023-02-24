package com.soha.foodplanner.ui.auth.signup;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.StringRes;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.soha.foodplanner.MyApp;
import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.ui.MainActivity;
import com.soha.foodplanner.ui.auth.signup.presenter.SignUpPresenter;
import com.soha.foodplanner.ui.auth.signup.presenter.SignUpPresenterListener;
import com.soha.foodplanner.ui.auth.signup.presenter.SignupPresenterFactory;
import com.soha.foodplanner.ui.common.fragment.BaseFragment;
import com.soha.foodplanner.ui.auth.signup.presenter.SignupState;


public class SignUpFragment extends BaseFragment<SignUpPresenter> implements SignUpPresenterListener {
    private Button buttonSignup;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;
    private ImageButton imageButtonBack;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_sign_up;
    }

    @Override
    protected void onDialogRetry(boolean retry) {
        if(retry) signup();
    }

    @Override
    protected Factory<SignUpPresenter> getPresenterFactory() {
        return new SignupPresenterFactory(((MyApp) ((MainActivity) requireHost()).getApplication()).getAuthRepository(), this);
    }

    @Override
    public void initViews(View view) {
        textInputLayoutName = view.findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = view.findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = view.findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = view.findViewById(R.id.textInputLayoutConfirmPassword);
        //
        textInputEditTextName = view.findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail = view.findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = view.findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = view.findViewById(R.id.textInputEditTextConfirmPassword);
        //
        buttonSignup = view.findViewById(R.id.buttonSignup);
        imageButtonBack = view.findViewById(R.id.imageButtonBack);
    }

    private void clearConfirmPasswordError() {
        textInputLayoutName.setErrorEnabled(false);
    }

    private void showConfirmPasswordError(@StringRes int message) {
        textInputLayoutConfirmPassword.setError(getText(message));
        textInputLayoutConfirmPassword.setErrorEnabled(true);
    }

    private void clearNameError() {
        textInputLayoutName.setErrorEnabled(false);
    }

    private void showNameError(@StringRes int message) {
        textInputLayoutName.setError(getText(message));
        textInputLayoutName.setErrorEnabled(true);
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
    public void setListeners() {
        buttonSignup.setOnClickListener(v -> signup());
        imageButtonBack.setOnClickListener(v -> navController.popBackStack());
    }

    private void signup() {
        String name = String.valueOf(textInputEditTextName.getText());
        String email = String.valueOf(textInputEditTextEmail.getText());
        String password = String.valueOf(textInputEditTextPassword.getText());
        String confirmPassword = String.valueOf(textInputEditTextConfirmPassword.getText());
        presenter.updateLoginState(name, email, password, confirmPassword);
        if (presenter.isInputsValid()) {
            presenter.signUp(name, email, password);
            clearNameError();
            clearEmailError();
            clearPasswordError();
            clearConfirmPasswordError();
        } else showFieldsErrors();
    }

    private void showFieldsErrors() {
        SignupState state = presenter.getState();
        if (state.isNameError())
            showNameError(state.getNameErrorMessage());
        else
            clearNameError();

        if (state.isEmailError())
            showEmailError(state.getEmailErrorMessage());
        else clearEmailError();

        if (state.isPasswordError())
            showPasswordError(state.getPasswordErrorMessage());
        else clearPasswordError();

        if (state.isConfirmPasswordError())
            showConfirmPasswordError(state.getPasswordErrorMessage());
        else clearConfirmPasswordError();
    }


    @Override
    public void showErrorDialog(int message) {
        navController.popBackStack();
        navController.navigate(SignUpFragmentDirections.actionSignUpFragmentToErrorDialogFragment(message));
    }

    @Override
    public void showLoadingDialog(boolean cancelable) {
        navController.navigate(SignUpFragmentDirections.actionSignUpFragmentToLoadingFragment(cancelable));
    }

    @Override
    public void showLoginDialog(int message) {
        navController.popBackStack();
        navController.navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginDialogFragment(message));
    }

    @Override
    public void showRetryDialog(int message) {
        navController.popBackStack();
        navController.navigate(SignUpFragmentDirections.actionSignUpFragmentToRetryDialogFragment(message));
    }

    @Override
    public void onSignupSuccess() {
        navController.popBackStack();
        navController.navigate(SignUpFragmentDirections.actionSignUpFragmentToMainFragment());
    }
}