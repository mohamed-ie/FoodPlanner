package com.soha.foodplanner.ui.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.soha.foodplanner.R;
import com.soha.foodplanner.ui.signup.presenter.SignUpPresenterImpl;
import com.soha.foodplanner.ui.signup.presenter.SignUpViewListener;


public class SignUpFragment extends Fragment implements SignUpViewListener {
    String enteredMail,enteredPassword,enteredConfirmedPassword;
    Button btnSignUp;
    EditText editTextMail, editTextPassword, editTextConfirmPassword;
    FirebaseAuth mAuth;
    CardView cardViewCancel;
    Button btnCancel;
    SignUpPresenterImpl signUpPresenter;
    private NavController navController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController= NavHostFragment.findNavController(SignUpFragment.this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_sign_up, container, false);
        mAuth=FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        signUpPresenter=new SignUpPresenterImpl(mAuth,SignUpFragment.this);
        setListeners();

    }

    private void updateEnteredStrings() {
        enteredMail= editTextMail.getText().toString();
        enteredPassword= editTextPassword.getText().toString();
        enteredConfirmedPassword= editTextConfirmPassword.getText().toString();

    }

    private void showErrors() {
            if(enteredMail.isEmpty()){
                Toast.makeText(getContext(), "Enter Mail", Toast.LENGTH_SHORT).show();
            }
            if(enteredPassword.isEmpty()){
                Toast.makeText(getContext(), "Enter Password", Toast.LENGTH_SHORT).show();
            }
            if(enteredConfirmedPassword.isEmpty()){
                Toast.makeText(getContext(), "Confirm password please", Toast.LENGTH_SHORT).show();
            }
        }

    private boolean validateInputs() {
        return !enteredConfirmedPassword.isEmpty()
                && !enteredMail.isEmpty()
                && !enteredPassword.isEmpty();
    }

    @Override
    public void onSuccess() {
        //navController.navigate(/*homeAction*/);
        stopLoading();
    }

    @Override
    public void onFailure(@StringRes int message) {
        stopLoading();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    void stopLoading(){
        navController.popBackStack();

    }

    private void setListeners() {
        btnSignUp.setOnClickListener((View v)-> {
            updateEnteredStrings();
            if(validateInputs()){
                signUpPresenter.signUp("soha",enteredMail,enteredPassword);
                navController.navigate(R.id.loadingFragment);
                //
            }else showErrors();
        });
    }
    private void initViews(View view) {
        editTextMail =view.findViewById(R.id.ed_mail);
        editTextPassword =view.findViewById(R.id.ed_password);
        editTextConfirmPassword =view.findViewById(R.id.confirm_password);
        btnSignUp =view.findViewById(R.id.sign_up_btn);
        btnCancel=view.findViewById(R.id.btn_cancelling);
    }
}