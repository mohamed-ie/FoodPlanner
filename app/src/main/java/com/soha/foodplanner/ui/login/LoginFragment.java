package com.soha.foodplanner.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.ui.common.presenter.factory.PresenterFactory;
import com.soha.foodplanner.ui.common.presenter.factory.PresenterFactoryImpl;
import com.soha.foodplanner.ui.common.presenter.store.PresenterStoreImpl;
import com.soha.foodplanner.ui.login.presenter.LoginPresenter;
import com.soha.foodplanner.ui.login.presenter.LoginPresenterFactory;
import com.soha.foodplanner.ui.login.presenter.LoginPresenterListener;
import com.soha.foodplanner.ui.login.presenter.LoginState;

import java.util.Objects;

public class LoginFragment extends Fragment implements LoginPresenterListener {
    public static final String RETRY = "RETRY";

    private LoginPresenter presenter;
    private NavController navController;
    private CheckBox checkBoxRememberMe;
    private Button buttonLogin;
    private ImageButton imageButtonBack;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextEmail;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private SharedPreferences sharedPreferences;


    //fragment lifecycle
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDependencies();
        setupObservers();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    //login presenter listener
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setListeners();
    }

    @Override
    public void onSuccess() {
        //   navController.navigate();
        Toast.makeText(requireContext(), R.string.login_successful, Toast.LENGTH_SHORT).show();
        updateRememberMe();
    }


    @Override
    public void onFailure(int messageResource) {
        showErrorDialog(messageResource);
    }

    //    @SuppressLint("NonConstantResourceId")
    @SuppressLint("NonConstantResourceId")
    private void showErrorDialog(int messageResource) {
        switch (messageResource) {
            case R.string.email_or_password_incorrect:
            case R.string.no_user_with_this_email:
                navController.navigate(LoginFragmentDirections.actionLoginFragmentToErrorDialogFragment(messageResource));
                break;
            default:
                navController.navigate(LoginFragmentDirections.actionLoginFragmentToRetryDialogFragment(messageResource));
        }
    }


    //
    private void initDependencies() {
        navController = NavHostFragment.findNavController(this);
        sharedPreferences = requireContext().getSharedPreferences(Constants.SHARED_PREFERENCES_APP, Context.MODE_PRIVATE);
        initPresenter();
    }

    private void initPresenter() {
        PresenterFactory<LoginPresenter> factory = PresenterFactoryImpl.getInstance(PresenterStoreImpl.getInstance());
        presenter = factory.create(LoginPresenter.TAG, new LoginPresenterFactory(FirebaseAuth.getInstance(), this));
    }


    private void setListeners() {
        buttonLogin.setOnClickListener(v -> login());
        imageButtonBack.setOnClickListener(v->navController.popBackStack());
    }

    private void initViews(@NonNull View view) {
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

    private void updateRememberMe() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.SHARED_PREFERENCES_KEY_REMEMBER_ME, checkBoxRememberMe.isChecked());
        editor.apply();
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


    private void setupObservers() {
        LiveData<Boolean> retryObserver = Objects.requireNonNull(navController
                        .getCurrentBackStackEntry())
                .getSavedStateHandle()
                .getLiveData(RETRY);

        retryObserver.observe(this, (Observer<Boolean>) retry -> {
            if (retry) {
                login();
            }
        });
    }
}