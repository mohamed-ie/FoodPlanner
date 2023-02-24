package com.soha.foodplanner.ui.common.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import org.jetbrains.annotations.Contract;

import java.util.Map;

public abstract class BaseDialogFragment extends DialogFragment {
    protected NavController navController;

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
        return inflater.inflate(getLayoutResource(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setListeners();
    }

    public void initViews(@NonNull View view) {

    }

    public abstract void setListeners();


    @NonNull
    @Contract(pure = true)
    private LifecycleEventObserver createNavBackStackEntryObserver(NavBackStackEntry navBackStackEntry, Map<String, ?> data) {
        return (source, event) -> {
            if (event.equals(Lifecycle.Event.ON_RESUME))
                for (String key : data.keySet())
                    navBackStackEntry.getSavedStateHandle().set(key, data.get(key));

        };
    }


    protected void sendDataViaBackStackEntry(Map<String, Object> data) {
        NavBackStackEntry navBackStackEntry = navController.getPreviousBackStackEntry();
        if (navBackStackEntry != null) {
            LifecycleEventObserver observer = createNavBackStackEntryObserver(navBackStackEntry, data);
            navBackStackEntry.getLifecycle().addObserver(observer);
            removeNavStackEntryObserver(navBackStackEntry, observer);
            navController.popBackStack();
        }
    }

    private void removeNavStackEntryObserver(NavBackStackEntry navBackStackEntry,
                                             LifecycleEventObserver observer) {
        getViewLifecycleOwner().getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event.equals(Lifecycle.Event.ON_DESTROY))
                navBackStackEntry.getLifecycle().removeObserver(observer);

        });
    }
}
