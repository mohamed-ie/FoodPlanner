package com.soha.foodplanner;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.data.data_source.remote.auth.AuthRemoteDataSource;
import com.soha.foodplanner.data.data_source.remote.auth.AuthRemoteDataSourceImpl;
import com.soha.foodplanner.data.data_source.remote.backup.BackupStrategy;
import com.soha.foodplanner.data.data_source.remote.backup.FirestoreBackupStrategy;
import com.soha.foodplanner.data.data_source.remote.meals.MealsRemoteDataSource;
import com.soha.foodplanner.data.data_source.remote.meals.MealsRemoteDataSourceImpl;
import com.soha.foodplanner.data.data_source.remote.restore.FirestoreRestoreStrategy;
import com.soha.foodplanner.data.data_source.remote.restore.RestoreStrategy;
import com.soha.foodplanner.data.data_source.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.data_source.remote.webservice.Webservice;
import com.soha.foodplanner.data.local.AppDatabase;
import com.soha.foodplanner.data.local.image_saver.InternalStorageImageSaver;
import com.soha.foodplanner.data.local.image_saver.InternalStorageImageSaverImpl;
import com.soha.foodplanner.data.mapper.MealMapper;
import com.soha.foodplanner.data.mapper.MealMapperImpl;
import com.soha.foodplanner.data.repository.MealsLocalDataSource;
import com.soha.foodplanner.data.repository.auth.AuthRepository;
import com.soha.foodplanner.data.repository.auth.AuthRepositoryImpl;
import com.soha.foodplanner.data.repository.meals.GlideImageDownloader;
import com.soha.foodplanner.data.repository.meals.MealsRepository;
import com.soha.foodplanner.data.repository.meals.MealsRepositoryImpl;

public class MyApp extends Application {
    private AuthRepository authRepository;
    private MealsRepository mealsRepository;
    private FirebaseFirestore firestore;
    private RequestManager glideRequestManager;


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
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        MealMapper mealMapper = new MealMapperImpl();
        TheMealDBWebService theMealDBWebService = Webservice.getInstance().getTheMealDBWebService();
        MealsRemoteDataSource remoteDataSource = new MealsRemoteDataSourceImpl(theMealDBWebService, mealMapper);
        InternalStorageImageSaver imageSaver = new InternalStorageImageSaverImpl(this.getDir(Constants.INTERNAL_STORAGE_IMAGE_DIRECTORY, Context.MODE_PRIVATE));
        GlideImageDownloader imageDownloader = new GlideImageDownloader(getGlideRequestManager().asBitmap());
        BackupStrategy backupStrategy = new FirestoreBackupStrategy(getFirestore(), firebaseAuth.getUid());
        RestoreStrategy restoreStrategy = new FirestoreRestoreStrategy(getFirestore(), firebaseAuth.getUid());

        mealsRepository = new MealsRepositoryImpl(
                remoteDataSource, localDataSource,
                imageSaver,
                imageDownloader, backupStrategy, restoreStrategy);
    }

    private FirebaseFirestore getFirestore() {
        if (firestore == null) {
            firestore = FirebaseFirestore.getInstance();
            firestore.setFirestoreSettings(getFirestoreSettings());
        }
        return firestore;
    }

    private FirebaseFirestoreSettings getFirestoreSettings() {
        return new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
    }

    public RequestManager getGlideRequestManager() {
        if (glideRequestManager == null)
            glideRequestManager = Glide.with(this);
        return glideRequestManager;
    }
}
