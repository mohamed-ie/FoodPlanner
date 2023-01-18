package com.soha.foodplanner.data.remote.start;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.soha.foodplanner.R;
import com.soha.foodplanner.ui.common.OnCompleteListener;

public class StartRemoteDataSourceImpl implements StartRemoteDataSource {
    private final FirebaseAuth firebaseAuth;
    private final OnCompleteListener onCompleteListener;

    public StartRemoteDataSourceImpl(FirebaseAuth firebaseAuth, OnCompleteListener onCompleteListener) {
        this.firebaseAuth = firebaseAuth;
        this.onCompleteListener = onCompleteListener;
    }

    @Override
    public void login(String idToken) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(task -> {
            if (task.isSuccessful())
                onCompleteListener.onSuccess();
            else
                onCompleteListener.onFailure(R.string.unexpected_error_try_again);
        });
    }
}
