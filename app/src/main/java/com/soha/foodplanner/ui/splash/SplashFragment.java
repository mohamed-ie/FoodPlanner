package com.soha.foodplanner.ui.splash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soha.foodplanner.R;


public class SplashFragment extends Fragment {
    @SuppressWarnings("FieldCanBeLocal")
    private final long DELAY_MILLIS = 3300;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarVisibility(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null)
            navigateAfterInitialDelay();
    }

    @Override
    public void onDestroy() {
        setStatusBarVisibility(true);
        super.onDestroy();
    }

    private void navigateAfterInitialDelay() {

        //TODO navigate to login
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            NavHostFragment.findNavController(this).navigate(R.id.startFragment);
        }, DELAY_MILLIS);

    }

    private void setStatusBarVisibility(boolean visible) {
        View decorView = requireActivity().getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (visible)
            uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }
}