package com.soha.foodplanner.ui.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsController;

import com.airbnb.lottie.LottieAnimationView;
import com.soha.foodplanner.R;


public class SplashFragment extends Fragment {
    private LottieAnimationView lottieAnimationView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarVisibility(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setListeners();
//        updateNextDestionation();
    }

    private void initViews(View view) {
        lottieAnimationView = view.findViewById(R.id.animationView);
    }

    private void setListeners() {
        lottieAnimationView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                NavHostFragment.findNavController(SplashFragment.this).navigate(SplashFragmentDirections.actionSplashFragmentToNavigation());
            }
        });
    }

    @Override
    public void onDestroy() {
        setStatusBarVisibility(true);
        super.onDestroy();
    }

    private void setStatusBarVisibility(boolean visible) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController windowInsetsController = requireActivity().getWindow().getDecorView().getWindowInsetsController();
            if (visible)
                windowInsetsController.show(WindowInsets.Type.statusBars());
            else
                windowInsetsController.hide(WindowInsets.Type.statusBars());
        } else {
            View decorView = requireActivity().getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            if (visible) uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}