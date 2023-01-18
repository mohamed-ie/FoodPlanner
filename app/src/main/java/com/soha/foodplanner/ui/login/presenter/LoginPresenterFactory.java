package com.soha.foodplanner.ui.login.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.remote.login.LoginRemoteDataSource;
import com.soha.foodplanner.data.remote.login.LoginRemoteDataSourceImpl;
import com.soha.foodplanner.data.repository.login.LoginRepository;
import com.soha.foodplanner.data.repository.login.LoginRepositoryImpl;

public class LoginPresenterFactory implements Factory<LoginPresenter> {
    private final FirebaseAuth firebaseAuth;
    private final LoginPresenterListener loginPresenterListener;

    public LoginPresenterFactory(FirebaseAuth firebaseAuth, LoginPresenterListener loginPresenterListener) {
        this.firebaseAuth = firebaseAuth;
        this.loginPresenterListener = loginPresenterListener;
    }

    @Override
    public LoginPresenter create() {
        LoginRemoteDataSource loginRemoteDataSource = new LoginRemoteDataSourceImpl(firebaseAuth, loginPresenterListener);
        LoginRepository loginRepository = new LoginRepositoryImpl(loginRemoteDataSource);
        return new LoginPresenterImpl(loginRepository);
    }
}
