package com.soha.foodplanner.ui.login.presenter;

import androidx.annotation.NonNull;

import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.data.repository.auth.AuthRepository;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenterImpl implements LoginPresenter {
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
        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
            disposables.add(d);
            listener.loginWithEmailAndPasswordLoading();
        }

        @Override
        public void onComplete() {
            listener.loginWithEmailAndPasswordSuccess();
        }

        @Override
        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
            listener.loginWithEmailAndPasswordError(e.getMessage());
        }
    }
}
