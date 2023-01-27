package com.soha.foodplanner.data.data_source.remote.auth;

import com.soha.foodplanner.data.local.model.User;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface AuthRemoteDataSource {

    Completable loginWithEmailAndPassword(String email, String password);

    Completable loginWithGoogle(String idToken);

    Completable logout();

    Single<Boolean> isLoggedIn();

    User getUser();
}
