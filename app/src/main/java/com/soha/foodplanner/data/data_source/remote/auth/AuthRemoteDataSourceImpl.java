package com.soha.foodplanner.data.data_source.remote.auth;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWebException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.model.User;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AuthRemoteDataSourceImpl implements AuthRemoteDataSource {
    private final FirebaseAuth firebaseAuth;

    public AuthRemoteDataSourceImpl(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public Completable loginWithEmailAndPassword(String email, String password) {
        return Completable.fromPublisher(publisher -> firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        publisher.onComplete();
                    else
                        publisher.onError(task.getException());
                }));
    }

    @Override
    public Completable loginWithGoogle(String idToken) {
        return Completable.fromPublisher(publisher -> {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken, null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(task -> {
                if (task.isSuccessful())
                    publisher.onComplete();
                else
                    publisher.onError(task.getException());
            });
        });
    }

    @Override
    public Completable logout() {
        return Completable.fromAction(firebaseAuth::signOut);
    }

    @Override
    public Single<Boolean> isLoggedIn() {
        return Single.fromCallable(() -> firebaseAuth.getCurrentUser() != null);
    }

    private int getMessageStringResource(Exception exception) {
        if (exception instanceof FirebaseAuthInvalidUserException)
            return R.string.no_user_with_this_email;
        else if (exception instanceof FirebaseAuthEmailException || exception instanceof FirebaseAuthInvalidCredentialsException)
            return R.string.email_or_password_incorrect;
        else if (exception instanceof FirebaseAuthWebException)
            return R.string.server_busy_try_again;
        else
            return R.string.unexpected_error_try_again;
    }

    @Override
    public User getUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        String photoUri = null;
        if (user.getPhotoUrl() != null)
            photoUri = user.getPhotoUrl().toString();
        return new User(user.getDisplayName(), user.getEmail(), photoUri);
    }
}
