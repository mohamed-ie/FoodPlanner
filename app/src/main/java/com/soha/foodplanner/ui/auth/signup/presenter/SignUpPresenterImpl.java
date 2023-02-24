package com.soha.foodplanner.ui.auth.signup.presenter;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthWebException;
import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.domain.data.repository.AuthRepository;
import com.soha.foodplanner.domain.exceptions.auth.EmailAreadyInUserException;
import com.soha.foodplanner.domain.exceptions.auth.InvalidEmailException;
import com.soha.foodplanner.domain.exceptions.auth.WeakPasswordException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignUpPresenterImpl extends SignUpPresenter {
    private final AuthRepository repository;
    private final SignUpPresenterListener listener;

    private final SignupState state = new SignupState();


    public SignUpPresenterImpl(AuthRepository repository, SignUpPresenterListener listener) {
        this.repository = repository;
        this.listener = listener;
    }

    @Override
    public void signUp(String name, String email, String password) {
        listener.showLoadingDialog(false);
        Disposable disposable = repository.signup(name, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onSignupSuccess, this::showSignupDialog);
        disposables.add(disposable);
    }

    @Override
    public void showSignupDialog(Throwable exception) {
        if (exception instanceof WeakPasswordException)
            listener.showErrorDialog(R.string.weak_password_error);
        else if (exception instanceof EmailAreadyInUserException)
            listener.showLoginDialog(R.string.email_exist_error);
        else if (exception instanceof InvalidEmailException)
            listener.showErrorDialog(R.string.not_valid_email);
        else if (exception instanceof FirebaseAuthWebException)
            listener.showRetryDialog(R.string.server_busy_try_again);
        else if (exception instanceof FirebaseNetworkException)
            listener.showRetryDialog(R.string.network_error);
        else
            listener.showRetryDialog(R.string.unexpected_error_try_again);
    }


    @Override
    public void updateLoginState(String name, String email, String password, String confirmPassword) {
        if (name.trim().isEmpty()) {
            state.setNameError(true);
            state.setNameErrorMessage(R.string.required);
        } else {
            state.setNameError(false);
        }

        if (email.isEmpty()) {
            state.setEmailError(true);
            state.setEmailErrorMessage(R.string.required);
        } else if (isEmailInForm(email)) {
            state.setEmailErrorMessage(R.string.not_formatted_email);
            state.setEmailError(true);
        } else {
            state.setEmailError(false);
        }

        if (password.isEmpty()) {
            state.setPasswordError(true);
            state.setPasswordErrorMessage(R.string.required);
        } else if (password.length() < 7) {
            state.setEmailError(true);
            state.setConfirmPasswordErrorMessage(R.string.password_less_than_7);
        } else
            state.setPasswordError(false);


        if (confirmPassword.isEmpty()) {
            state.setConfirmPasswordError(true);
            state.setConfirmPasswordErrorMessage(R.string.required);
        } else if (!password.equals(confirmPassword)) {
            state.setConfirmPasswordError(true);
            state.setConfirmPasswordErrorMessage(R.string.passwords_doest_match);
        } else
            state.setConfirmPasswordError(false);

    }

    @Override
    public SignupState getState() {
        return state;
    }

    @Override
    public boolean isInputsValid() {
        return !state.isNameError()
                && !state.isEmailError()
                && !state.isPasswordError()
                && !state.isConfirmPasswordError();
    }

    private boolean isEmailInForm(@NonNull String email) {
        return !email.matches(Constants.PATTERN_EMAIL);
    }
}
