package com.soha.foodplanner.ui.signup.presenter;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
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

                                }else if(exception instanceof FirebaseAuthWeakPasswordException){
                                    signUpViewListener.onFailure(R.string.weak_password_error);
                                }else if(exception instanceof FirebaseAuthEmailException){
                                    signUpViewListener.onFailure(R.string.email_exist_error);
                                }else if(exception instanceof FirebaseNetworkException){
                                    signUpViewListener.onFailure(R.string.network_error);
                                }


                            }

                        }
                    }
                });
    }
}
