package com.soha.foodplanner.data.remote.start;

import com.google.firebase.auth.AuthCredential;

public interface StartRemoteDataSource {
    void login(String idToken);
}
