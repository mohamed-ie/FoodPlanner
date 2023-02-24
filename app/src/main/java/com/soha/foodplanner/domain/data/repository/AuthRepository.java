package com.soha.foodplanner.domain.data.repository;

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

    Completable signup(String name, String email, String password);
}
