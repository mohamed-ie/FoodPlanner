package com.soha.foodplanner.ui.start.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.remote.start.StartRemoteDataSource;
import com.soha.foodplanner.data.remote.start.StartRemoteDataSourceImpl;
import com.soha.foodplanner.data.repository.start.StartRepository;
import com.soha.foodplanner.data.repository.start.StartRepositoryImpl;
import com.soha.foodplanner.ui.common.presenter.Presenter;

public class StartPresenterFactory implements Factory<StartPresenter>{
    private final FirebaseAuth firebaseAuth;
    private final StartPresenterListener startPresenterListener;

    public StartPresenterFactory(FirebaseAuth firebaseAuth, StartPresenterListener startPresenterListener) {
        this.firebaseAuth = firebaseAuth;
        this.startPresenterListener = startPresenterListener;
    }


    @Override
    public StartPresenter create() {
        StartRemoteDataSource remoteDataSource = new StartRemoteDataSourceImpl(firebaseAuth,startPresenterListener);
        StartRepository startRepository = new StartRepositoryImpl(remoteDataSource);
        return new StartPresenterImpl(startRepository);
    }
}
