package com.soha.foodplanner.ui.profile.presenter;

import com.soha.foodplanner.data.repository.auth.AuthRepository;
import com.soha.foodplanner.ui.profile.ProfileState;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenterImpl implements ProfilePresenter {
    private final AuthRepository repository;
    private final ProfilePresenterListener listener;

    private final ProfileState state = new ProfileState();

    public ProfilePresenterImpl(AuthRepository repository, ProfilePresenterListener listener) {
        this.repository = repository;
        this.listener = listener;
        state.setUser(repository.getUser());
    }

    @Override
    public void logout() {
        repository.logout()
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LogoutObserver());
    }

    private class LogoutObserver implements CompletableObserver {

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            listener.onLogoutLoading();
        }

        @Override
        public void onComplete() {
            listener.onLogoutSuccess();
        }

        @Override
        public void onError(@NonNull Throwable e) {
            listener.onLogoutError(e.getMessage());
        }
    }

    @Override
    public ProfileState getState() {
        return state;
    }

    @Override
    public void updateRememberMe() {
        repository.updateRememberMe(false);
    }
}
