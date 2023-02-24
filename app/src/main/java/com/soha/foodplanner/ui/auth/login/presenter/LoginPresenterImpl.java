package com.soha.foodplanner.ui.auth.login.presenter;

import androidx.annotation.NonNull;

import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.domain.data.repository.AuthRepository;
import com.soha.foodplanner.domain.exceptions.NetworkException;
import com.soha.foodplanner.domain.exceptions.ServerBusyExceptionException;
import com.soha.foodplanner.domain.exceptions.auth.AuthException;
import com.soha.foodplanner.domain.exceptions.auth.EmailOrPasswordIncorrectException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenterImpl extends LoginPresenter {
    private final LoginState state = new LoginState();
    private final AuthRepository repository;
    private final LoginPresenterListener listener;

    public LoginPresenterImpl(AuthRepository repository, LoginPresenterListener listener) {
        this.repository = repository;
        this.listener = listener;
    }

    @Override
    public void login(String email, String password) {
        repository.loginWithEmailAndPassword(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoginWithEmailAndPasswordObserver());
    }

    public boolean isInputsValid() {
        return !state.isEmailError() && !state.isPasswordError();
    }

    @Override
    public LoginState getLoginState() {
        return state;
    }

    @Override
    public void updateLoginState(String email, String password) {
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
        } else {
            state.setPasswordError(false);
        }
    }

    @Override
    public void cancelLogin() {
        destroy();
    }

    @Override
    public void updateRememberMe(boolean rememberMe) {
        repository.updateRememberMe(rememberMe);
    }

    private boolean isEmailInForm(@NonNull String email) {
        return !email.matches(Constants.PATTERN_EMAIL);
    }

    private class LoginWithEmailAndPasswordObserver implements CompletableObserver {

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            disposables.add(d);
            listener.onLoginLoading();
        }

        @Override
        public void onComplete() {
            listener.onLoginSuccess();
        }

        @Override
        public void onError(@NonNull Throwable e) {
            if (e instanceof AuthException)
                if (e instanceof EmailOrPasswordIncorrectException)
                    listener.showErrorDialog(R.string.email_or_password_incorrect);
                else listener.showSignupDialog(R.string.no_user_with_this_email);
            else if (e instanceof ServerBusyExceptionException)
                listener.showRetryDialog(R.string.server_busy_try_again);
            else if (e instanceof NetworkException)
                listener.showRetryDialog(R.string.network_error);
            else listener.showErrorDialog(R.string.unexpected_error_try_again);
        }
    }
}
