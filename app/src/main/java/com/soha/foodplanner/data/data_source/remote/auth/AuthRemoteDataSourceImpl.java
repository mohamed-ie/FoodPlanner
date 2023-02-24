package com.soha.foodplanner.data.data_source.remote.auth;

import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseAuthWebException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.soha.foodplanner.data.local.model.User;
import com.soha.foodplanner.domain.data.data_source.remote.AuthRemoteDataSource;
import com.soha.foodplanner.domain.exceptions.NetworkException;
import com.soha.foodplanner.domain.exceptions.UnexpectedErrorOccurredException;
import com.soha.foodplanner.domain.exceptions.auth.InvalidEmailException;
import com.soha.foodplanner.domain.exceptions.auth.NoUserWithThisEmailException;
import com.soha.foodplanner.domain.exceptions.auth.EmailOrPasswordIncorrectException;
import com.soha.foodplanner.domain.exceptions.ServerBusyExceptionException;
import com.soha.foodplanner.domain.exceptions.auth.EmailAreadyInUserException;
import com.soha.foodplanner.domain.exceptions.auth.WeakPasswordException;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AuthRemoteDataSourceImpl implements AuthRemoteDataSource {
    private final FirebaseAuth firebaseAuth;

    public AuthRemoteDataSourceImpl(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public Completable loginWithEmailAndPassword(String email, String password) {
        return Completable.create(source -> firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        source.onComplete();
                    else
                        source.onError(getLoginException(task.getException()));
                }));
    }

    @Override
    public Completable loginWithGoogle(String idToken) {
        return Completable.create(source -> {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken, null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(task -> {
                if (task.isSuccessful())
                    source.onComplete();
                else
                    source.onError(new UnexpectedErrorOccurredException());
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

    @Override
    public User getUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        String photoUri = null;
        if (user.getPhotoUrl() != null)
            photoUri = user.getPhotoUrl().toString();
        return new User(user.getDisplayName(), user.getEmail(), photoUri);
    }

    @Override
    public Completable signup(String name, String email, String password) {
        return Completable.create(source -> {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                            source.onComplete();
                        else source.onError(getSignupException(task.getException()));
                    });
        }).doOnComplete(() -> firebaseAuth.getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(name).build()));
    }

    private Exception getLoginException(Exception exception) {
        if (exception instanceof FirebaseAuthInvalidUserException)
            return new NoUserWithThisEmailException();
        else if (exception instanceof FirebaseAuthEmailException || exception instanceof FirebaseAuthInvalidCredentialsException)
            return new EmailOrPasswordIncorrectException();
        else if (exception instanceof FirebaseAuthWebException)
            return new ServerBusyExceptionException();
        else if (exception instanceof FirebaseNetworkException)
            return new NetworkException();
        else
            return new UnexpectedErrorOccurredException();

    }

    private Exception getSignupException(Exception exception) {
        if (exception instanceof FirebaseAuthWeakPasswordException)
            return new WeakPasswordException();
        else if (exception instanceof FirebaseAuthUserCollisionException)
            return new EmailAreadyInUserException();
        else if (exception instanceof FirebaseAuthInvalidCredentialsException)
            return new InvalidEmailException();
        else if (exception instanceof FirebaseAuthWebException)
            return new ServerBusyExceptionException();
        else if (exception instanceof FirebaseNetworkException)
            return new NetworkException();
        else
            return new UnexpectedErrorOccurredException();
    }


}
