package com.soha.foodplanner.ui.login.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.remote.login.LoginRemoteDataSource;
import com.soha.foodplanner.data.remote.login.LoginRemoteDataSourceImpl;
import com.soha.foodplanner.data.repository.login.LoginRepository;
import com.soha.foodplanner.data.repository.login.LoginRepositoryImpl;
import com.soha.foodplanner.ui.common.OnCompleteListener;

public class LoginPresenterFactory implements Factory<LoginPresenter> {
    private final FirebaseAuth firebaseAuth;
    private final OnCompleteListener onCompleteListener;

    public LoginPresenterFactory(FirebaseAuth firebaseAuth, OnCompleteListener onCompleteListener) {
        this.firebaseAuth = firebaseAuth;
        this.onCompleteListener = onCompleteListener;
    }

    @Override
    public LoginPresenter create() {
        LoginRemoteDataSource loginRemoteDataSource = new LoginRemoteDataSourceImpl(firebaseAuth, onCompleteListener);
        LoginRepository loginRepository = new LoginRepositoryImpl(loginRemoteDataSource);
        return new LoginPresenterImpl(loginRepository);
    }
}
