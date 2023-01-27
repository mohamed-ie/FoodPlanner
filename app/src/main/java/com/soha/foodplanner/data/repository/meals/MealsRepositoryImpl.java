package com.soha.foodplanner.data.repository.meals;

import com.soha.foodplanner.data.local.entities.FavouriteMealsWithMeal;
import com.soha.foodplanner.data.local.entities.Meal;
import com.soha.foodplanner.data.local.entities.PlanedMealWithMeal;
import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.data_source.remote.meals.MealsRemoteDataSource;
import com.soha.foodplanner.data.repository.MealsLocalDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealsRepositoryImpl implements MealsRepository {
    private final MealsRemoteDataSource remoteDataSource;
    private final MealsLocalDataSource localDataSource;

    public MealsRepositoryImpl(MealsRemoteDataSource remoteDataSource, MealsLocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public Single<List<MinMeal>> getMealsByCategory(String category) {
        return remoteDataSource.getMealsByCategory(category);
    }

    @Override
    public Single<List<MinMeal>> getMealsByArea(String area) {
        return remoteDataSource.getMealsByArea(area);
    }

    @Override
    public Single<List<MinMeal>> loadMeals(int count) {
        return null;
    }

    @Override
    public Single<List<MinMeal>> loadByCategory(String category) {
        return remoteDataSource.getMealsByCategory(category);
    }

    @Override
    public Single<List<String>> searchByFirstLetter(char c) {
        return remoteDataSource.getAllMealsByFirstLetter(c);
    }

    @Override
    public Single<List<String>> getAllAres() {
        return remoteDataSource.getAreas();
    }

    @Override
    public Single<List<MinIngredient>> getAllIngredients() {
        return remoteDataSource.getAllIngredients();
    }

    @Override
    public Single<List<String>> getAllCategories() {
        return remoteDataSource.getAllCategories();
    }

    @Override
    public Single<Meal> selectMealById(String id) {
        return localDataSource.selectMealById(id);
    }

    @Override
    public Completable deleteFavMeal(FavouriteMealsWithMeal mealFav) {
        return localDataSource.deleteFavMeal(mealFav);
    }

    @Override
    public Completable deletePlannedMeal(PlanedMealWithMeal planedMealWithMeal) {
        return localDataSource.deletePlannedMeal(planedMealWithMeal);
    }

    @Override
    public Flowable<List<FavouriteMealsWithMeal>> getFavMeal() {
        return localDataSource.getFavMeal();
    }

    @Override
    public Flowable<List<PlanedMealWithMeal>> getPlanedMeal() {
        return localDataSource.getPlanedMeal();
    }

    @Override
    public Completable insertFavMeal(MinMeal minMeal) {
//        return localDataSource.insertFavMeal(completeMeal);
        return null;
    }

    @Override
    public Completable insertPlanMeal(CompleteMeal completeMeal, long date, String mealTime) {
        return localDataSource.insertPlanMeal(completeMeal, date, mealTime);
    }

    @Override
    public Flowable<Long> getAllFavouriteMealsIds() {
        return localDataSource.getAllFavouriteMealsIds();
    }


}
