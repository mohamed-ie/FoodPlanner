package com.soha.foodplanner.data.repository.start;

import com.google.firebase.auth.AuthCredential;

public interface StartRepository {
    void login(String idToken);
}
