package com.soha.foodplanner.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.ui.login.presenter.LoginPresenter;
import com.soha.foodplanner.ui.login.presenter.LoginPresenterImpl;
import com.soha.foodplanner.ui.login.presenter.LoginPresenterViewListener;

public class LoginFragment extends Fragment implements LoginPresenterViewListener {
    private LoginPresenter presenter;
    private NavController navController;
    private CheckBox checkBoxRememberMe;
    private Button buttonLogin;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextEmail;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private SharedPreferences sharedPreferences;
    private AlertDialog.Builder alertDialogBuilder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        sharedPreferences = requireContext().getSharedPreferences(Constants.SHARED_PREFERENCES_APP, Context.MODE_PRIVATE);
        presenter = new LoginPresenterImpl(FirebaseAuth.getInstance(), this);
        alertDialogBuilder = new AlertDialog.Builder(requireContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setListeners();
    }

    private void setListeners() {
        buttonLogin.setOnClickListener(v -> login(textInputEditTextEmail.getText().toString(),
                textInputEditTextPassword.getText().toString())
        );
    }

    private void updateRememberMe() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.SHARED_PREFERENCES_KEY_REMEMBER_ME, checkBoxRememberMe.isChecked());
        editor.apply();
    }

    private void login(String email, String password) {
        if (isInputsValid(email, password))
            presenter.login(email, password);
        else showFieldsErrors(email, password);
    }

    private void showFieldsErrors(@NonNull String email, String password) {
        if (email.isBlank())
            showEmailError(R.string.required);
        else if (!isEmailInForm(email))
            showEmailError(R.string.not_email);
        else clearEmailError();

        if (password.isBlank())
            showPasswordError(R.string.required);
        else clearPasswordError();
    }

    private boolean isEmailInForm(@NonNull String email) {
        return email.matches(Constants.PATTERN_EMAIL);
    }

    private void clearPasswordError() {
        textInputLayoutPassword.setErrorEnabled(false);
    }

    private void showPasswordError(@StringRes int message) {
        textInputLayoutPassword.setError(getText(message));
        textInputLayoutPassword.setErrorEnabled(true);
    }

    private void clearEmailError() {
        textInputLayoutEmail.setErrorEnabled(false);
    }

    private void showEmailError(@StringRes int message) {
        textInputLayoutEmail.setError(getText(message));
        textInputLayoutEmail.setErrorEnabled(true);
    }

    private boolean isInputsValid(@NonNull String email, String password) {
        return !email.isBlank() && !password.isBlank() && isEmailInForm(email);
    }

    private void initViews(@NonNull View view) {
        textInputEditTextEmail = view.findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = view.findViewById(R.id.textInputEditTextPassword);
        textInputLayoutEmail = view.findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = view.findViewById(R.id.textInputLayoutPassword);
        //
        buttonLogin = view.findViewById(R.id.buttonLogin);
        //
        checkBoxRememberMe = view.findViewById(R.id.checkBoxRememberMe);
    }

    @Override
    public void onSuccess() {
        //   navController.navigate();
        Toast.makeText(requireContext(), R.string.login_successful, Toast.LENGTH_SHORT).show();
        updateRememberMe();
    }


    @Override
    public void onFailure(int messageResource) {
        alertDialogBuilder
                .setTitle(R.string.login_failed)
                .setMessage(messageResource)
                .show();
    }

}