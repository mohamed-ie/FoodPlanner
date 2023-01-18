package com.soha.foodplanner.data.repository.start;

import com.google.firebase.auth.AuthCredential;
import com.soha.foodplanner.data.remote.start.StartRemoteDataSource;

public class StartRepositoryImpl implements StartRepository{
    private final StartRemoteDataSource remoteDataSource;

    public StartRepositoryImpl(StartRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void login(String idToken) {
        remoteDataSource.login(idToken);
    }
}
