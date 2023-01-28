package com.soha.foodplanner.ui.start.presenter;

import com.soha.foodplanner.data.repository.auth.AuthRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class StartPresenterImpl implements StartPresenter {
    private final AuthRepository repository;
    private final StartPresenterListener listener;

    public StartPresenterImpl(AuthRepository repository, StartPresenterListener listener) {
        this.repository = repository;
        this.listener = listener;
    }

    @Override
    public void login(String idToken) {
        repository.loginWithGoogle(idToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoginWithGoogleObserver());
    }

    @Override
    public void isLoggedIn() {
        disposables.add(repository.rememberMe()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        listener.loggedIn();
                    }
                }));
    }

    @Override
    public void updateRememberMe() {
        repository.updateRememberMe(true);
    }

    private class LoginWithGoogleObserver implements CompletableObserver {

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            disposables.add(d);
            listener.onLoginWithGoogleLoading();
        }

        @Override
        public void onComplete() {
            listener.onLoginWithGoogleSuccess();
        }

        @Override
        public void onError(@NonNull Throwable e) {
            listener.onLoginWithGoogleError(e.getMessage());
        }
    }

}
