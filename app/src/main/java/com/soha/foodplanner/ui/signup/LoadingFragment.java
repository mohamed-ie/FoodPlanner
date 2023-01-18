package com.soha.foodplanner.ui.signup;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.soha.foodplanner.R;


public class LoadingFragment extends DialogFragment {
    Button button_cancel;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,R.style.Theme_FoodPlanner_Dialog);
        navController= NavHostFragment.findNavController(LoadingFragment.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        initViews(view);
        setListeners();
    }
    private void initViews(View view) {
        button_cancel =view.findViewById(R.id.btn_cancelling);

    }
    private void setListeners() {
        button_cancel.setOnClickListener((View v)-> {
            stopLoading();

        });
    }
    void stopLoading(){
        navController.popBackStack();
    }
}