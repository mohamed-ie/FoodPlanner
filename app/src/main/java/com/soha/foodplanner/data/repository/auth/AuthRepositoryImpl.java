package com.soha.foodplanner.data.repository.auth;

import android.content.SharedPreferences;

import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.domain.data.data_source.remote.AuthRemoteDataSource;
import com.soha.foodplanner.data.local.model.User;
import com.soha.foodplanner.domain.data.repository.AuthRepository;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthRepositoryImpl implements AuthRepository {
    private final AuthRemoteDataSource authRemoteDataSource;
    private final SharedPreferences sharedPreferences;

    public AuthRepositoryImpl(AuthRemoteDataSource authRemoteDataSource, SharedPreferences sharedPreferences) {
        this.authRemoteDataSource = authRemoteDataSource;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public Completable loginWithEmailAndPassword(String email, String password) {
        return authRemoteDataSource.loginWithEmailAndPassword(email, password);
    }

    @Override
    public Completable loginWithGoogle(String idToken) {
        return authRemoteDataSource.loginWithGoogle(idToken);
    }

    @Override
    public Completable logout() {
        return authRemoteDataSource.logout();
    }

    @Override
    public Single<Boolean> rememberMe() {
        return Single.fromCallable(() -> sharedPreferences.getBoolean(Constants.SHARED_PREFERENCES_KEY_REMEMBER_ME, false)).subscribeOn(Schedulers.io());
    }

    @Override
    public void updateRememberMe(boolean rememberMe) {
        sharedPreferences.edit()
                .putBoolean(Constants.SHARED_PREFERENCES_KEY_REMEMBER_ME, rememberMe)
                .apply();
    }

    @Override
    public User getUser() {
        return authRemoteDataSource.getUser();
    }

    @Override
    public Completable signup(String name, String email, String password) {
        return authRemoteDataSource.signup(name,email,password);
    }
}
