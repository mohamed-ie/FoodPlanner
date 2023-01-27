package com.soha.foodplanner.data.repository.auth;

import com.soha.foodplanner.data.local.model.User;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface AuthRepository {
    Completable loginWithEmailAndPassword(String email, String password);

    Completable loginWithGoogle(String tokenId);

    Completable logout();

    Single<Boolean> rememberMe();

    void updateRememberMe(boolean rememberMe);

    User getUser();
}
