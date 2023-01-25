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
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.soha.foodplanner.R;
import com.soha.foodplanner.ui.login.LoginFragment;

import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseDialogFragment extends DialogFragment {
    protected TextView textViewMessage;
    protected NavController navController;
    private final Map<String, Object> data = new HashMap<>();

    @LayoutRes
    public abstract int getLayoutResource();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_FoodPlanner_Dialog);
        navController = NavHostFragment.findNavController(this);
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
    }

    public void initViews(@NonNull View view) {
        textViewMessage = view.findViewById(R.id.textViewMessage);
    }

    public abstract void setListeners();


    @NonNull
    @Contract(pure = true)
    private LifecycleEventObserver createNavBackStackEntryObserver(NavBackStackEntry navBackStackEntry, Map<String, ?> data) {
        return (LifecycleEventObserver) (source, event) -> {
            if (event.equals(Lifecycle.Event.ON_RESUME) ) {
                for (String key : data.keySet())
                    navBackStackEntry.getSavedStateHandle().set(key, data.get(key));
            }
        };
    }


    protected void sendDataViaBackStackEntry(Map<String,Object> data) {
//        NavBackStackEntry navBackStackEntry = navController.getBackStackEntry(R.id.loginFragment);
        NavBackStackEntry navBackStackEntry = navController.getPreviousBackStackEntry();
        //data to be sent via backstack entry
        //
        //create observer
        LifecycleEventObserver observer = createNavBackStackEntryObserver(navBackStackEntry, data);
        //register observer
        navBackStackEntry.getLifecycle().addObserver(observer);
        //remove observer
        removeNavStackEntryObserver(navBackStackEntry, observer, data);
        navController.popBackStack();
    }

    private void removeNavStackEntryObserver(NavBackStackEntry navBackStackEntry,
                                             LifecycleEventObserver observer,
                                             Map<String, Object> data) {
        getViewLifecycleOwner().getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event.equals(Lifecycle.Event.ON_DESTROY)) {
                for (String key : data.keySet())
                    navBackStackEntry.getSavedStateHandle().set(key, data.get(key));
                navBackStackEntry.getLifecycle().removeObserver(observer);
            }
        });
    }
}
