package com.soha.foodplanner.ui.main.profile;

import com.google.firebase.auth.FirebaseAuth;
import com.soha.foodplanner.data.local.model.User;

public class ProfileState {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
