package com.soha.foodplanner.data.repository.login;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWebException;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.remote.login.LoginRemoteDataSource;

public class LoginRepositoryImpl implements LoginRepository {
    private final LoginRemoteDataSource loginRemoteDataSource;

    public LoginRepositoryImpl(LoginRemoteDataSource loginRemoteDataSource) {
        this.loginRemoteDataSource = loginRemoteDataSource;
    }

    @Override
    public void login(String email, String password) {
        loginRemoteDataSource.login(email,password);
    }

}
