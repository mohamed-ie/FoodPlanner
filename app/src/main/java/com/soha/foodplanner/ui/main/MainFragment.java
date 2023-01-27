package com.soha.foodplanner.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.data.mapper.MealMapper;
import com.soha.foodplanner.data.mapper.MealMapperImpl;
import com.soha.foodplanner.data.data_source.remote.meals.MealsRemoteDataSource;
import com.soha.foodplanner.data.data_source.remote.meals.MealsRemoteDataSourceImpl;
import com.soha.foodplanner.data.data_source.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.data_source.remote.webservice.Webservice;
import com.soha.foodplanner.data.repository.meals.MealsRepository;
import com.soha.foodplanner.data.repository.meals.MealsRepositoryImpl;

public class MainFragment extends Fragment {
    private NavController navController;
    private BottomNavigationView bottomNavigationView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupBottomNavigationWithNavController(view);
        manageBottomNavigationVisibility();
    }

    private void manageBottomNavigationVisibility() {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            boolean showAppBar = false;
            if (arguments != null) {
                showAppBar = arguments.getBoolean(Constants.ARGUMENT_BOTTOM_APP_BAR_VISIBILITY, true);
            }
            if (!showAppBar) {
                bottomNavigationView.setVisibility(View.VISIBLE);
            } else {
                bottomNavigationView.setVisibility(View.GONE);

            }
        });
    }

    private void setupBottomNavigationWithNavController(View view) {
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }


    public void hideBottomNavigation(){
        bottomNavigationView.setVisibility(View.GONE);
    }

    public void showBottomNavigation(){
        bottomNavigationView.setVisibility(View.VISIBLE);
    }


}