package com.soha.foodplanner.ui.login.presenter;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWebException;
import com.soha.foodplanner.R;

public class LoginPresenterImpl implements LoginPresenter {
    private final FirebaseAuth firebaseAuth;
    private final LoginPresenterViewListener loginPresenterViewListener;

    public LoginPresenterImpl(FirebaseAuth firebaseAuth, LoginPresenterViewListener loginPresenterViewListener) {
        this.firebaseAuth = firebaseAuth;
        this.loginPresenterViewListener = loginPresenterViewListener;
    }

    @Override
    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this::onCompleteListener);
    }

    private void onCompleteListener(Task<AuthResult> task) {
        if (task.isSuccessful())
            loginPresenterViewListener.onSuccess();
        else
            loginPresenterViewListener.onFailure(getMessageStringResource(task.getException()));
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

}
