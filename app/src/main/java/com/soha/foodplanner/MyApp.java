package com.soha.foodplanner;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.data.data_source.remote.auth.AuthRemoteDataSource;
import com.soha.foodplanner.data.data_source.remote.auth.AuthRemoteDataSourceImpl;
import com.soha.foodplanner.data.data_source.remote.meals.MealsRemoteDataSource;
import com.soha.foodplanner.data.data_source.remote.meals.MealsRemoteDataSourceImpl;
import com.soha.foodplanner.data.data_source.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.data_source.remote.webservice.Webservice;
import com.soha.foodplanner.data.local.AppDatabase;
import com.soha.foodplanner.data.mapper.MealMapper;
import com.soha.foodplanner.data.mapper.MealMapperImpl;
import com.soha.foodplanner.data.repository.MealsLocalDataSource;
import com.soha.foodplanner.data.repository.auth.AuthRepository;
import com.soha.foodplanner.data.repository.auth.AuthRepositoryImpl;
import com.soha.foodplanner.data.repository.meals.MealsRepository;
import com.soha.foodplanner.data.repository.meals.MealsRepositoryImpl;

public class MyApp extends Application {
    private AuthRepository authRepository;
    private MealsRepository mealsRepository;


    public AuthRepository getAuthRepository() {
        if (authRepository == null)
            initAuthRepository();
        return authRepository;
    }

    private void initAuthRepository() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_APP, Context.MODE_PRIVATE);
        AuthRemoteDataSource authRemoteDataSource = new AuthRemoteDataSourceImpl(firebaseAuth);
        authRepository = new AuthRepositoryImpl(authRemoteDataSource, sharedPreferences);
    }

    public MealsRepository getMealsRepository() {
        if (mealsRepository == null)
            initMealsRepository();
        return mealsRepository;
    }

    private void initMealsRepository() {
        //
        AppDatabase appDatabase = AppDatabase.getInstance(this);
        MealsLocalDataSource localDataSource = new MealsLocalDataSource(appDatabase);
        //
        MealMapper mealMapper = new MealMapperImpl();
        TheMealDBWebService theMealDBWebService = Webservice.getInstance().getTheMealDBWebService();
        MealsRemoteDataSource remoteDataSource = new MealsRemoteDataSourceImpl(theMealDBWebService, mealMapper);
        mealsRepository = new MealsRepositoryImpl(remoteDataSource, localDataSource);
    }

    private void updateFirestoreSettings() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        FirebaseFirestore.getInstance().setFirestoreSettings(settings);
    }


}
