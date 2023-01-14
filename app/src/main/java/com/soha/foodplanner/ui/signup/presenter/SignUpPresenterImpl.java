package com.soha.foodplanner.ui.signup.presenter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.soha.foodplanner.R;

public class SignUpPresenterImpl implements SignUpPresenter{

    private final FirebaseAuth firebaseAuth;
    private final SignUpViewListener signUpViewListener;

    public SignUpPresenterImpl(FirebaseAuth firebaseAuth, SignUpViewListener signUpViewListener) {
        this.firebaseAuth = firebaseAuth;
        this.signUpViewListener = signUpViewListener;
    }

    @Override
    public void signUp(String name, String email, String password) {
        firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //todo add name to firebase user
                            firebaseAuth.getCurrentUser();
                            signUpViewListener.onSuccess();
                        } else {
                            Exception exception=task.getException();
                            if(exception!=null){
                                if(exception instanceof FirebaseAuthUserCollisionException){
                                    signUpViewListener.onFailure(R.string.email_exist_error);

                                }


                            }

                        }
                    }
                });
    }
}
