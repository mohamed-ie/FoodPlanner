package com.soha.foodplanner.ui.StartScreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.soha.foodplanner.R;


public class StartFragment extends Fragment {
    private Button btnSignupWithMail;
    private NavController navController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       navController= NavHostFragment.findNavController(StartFragment.this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setListeners();
    }

    private void setListeners() {
        btnSignupWithMail.setOnClickListener( view ->
                navController.navigate(R.id.signUpFragment)
        );
    }

    private void initViews(View view) {
        btnSignupWithMail =view.findViewById(R.id.buttonSignUpWithMail);
    }
}