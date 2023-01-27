package com.soha.foodplanner.data.repository.auth;

import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;
import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.data.data_source.remote.auth.AuthRemoteDataSource;
import com.soha.foodplanner.data.local.model.User;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

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
        return Single.fromPublisher(publisher -> publisher.onNext(sharedPreferences.getBoolean(Constants.SHARED_PREFERENCES_KEY_REMEMBER_ME, false)));
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
}
