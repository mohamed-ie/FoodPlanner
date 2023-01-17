package com.soha.foodplanner.ui.common.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavArgs;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.soha.foodplanner.R;

public abstract class BaseDialogFragment<S extends NavArgs> extends DialogFragment {
    protected S args;
    protected TextView textViewMessage;
    protected NavController navController;

    @LayoutRes
    public abstract int getLayoutResource();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        args=getSafeArgs();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requireDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(getLayoutResource(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setListeners();
        if (args != null)
            updateUiFromSafeArgs(args);
    }

    public abstract S getSafeArgs();



    public void initViews(View view) {
        textViewMessage = view.findViewById(R.id.textViewMessage);
    }

    public abstract void setListeners();

    public void updateUiFromSafeArgs(S args) {

    }

    ;

}
