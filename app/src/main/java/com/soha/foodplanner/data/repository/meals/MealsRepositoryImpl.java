package com.soha.foodplanner.data.repository.meals;

import android.util.Pair;

import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.data_source.remote.meals.MealsRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealsRepositoryImpl implements MealsRepository {
    private final MealsRemoteDataSource mealsRemoteDataSource;

    public MealsRepositoryImpl(MealsRemoteDataSource mealsRemoteDataSource) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
    }

    @Override
    public Single<List<MinMeal>> getMealsByCategory(String category) {
        return mealsRemoteDataSource.getMealsByCategory(category);
    }

    @Override
    public Single<List<MinMeal>> getMealsByArea(String area) {
        return mealsRemoteDataSource.getMealsByArea(area);
    }

    @Override
    public Single<List<MinMeal>> loadMeals(int count) {
        return null;
    }

    @Override
    public Single<List<MinMeal>> loadByCategory(String category) {
        return mealsRemoteDataSource.getMealsByCategory(category);
    }

    @Override
    public Single<List<String>> searchByFirstLetter(char c) {
        return mealsRemoteDataSource.getAllMealsByFirstLetter(c);
    }

    @Override
    public Single<List<String>> getAllAres() {
        return mealsRemoteDataSource.getAreas();
    }

    @Override
    public Single<List<MinIngredient>> getAllIngredients() {
        return mealsRemoteDataSource.getAllIngredients();
    }

    @Override
    public Single<List<String>> getAllCategories() {
        return mealsRemoteDataSource.getAllCategories();
    }

    @Override
    public Single<List<MinMeal>> getMealsByIngredient(String ingredient) {
        return mealsRemoteDataSource.getAllMealsByIngredient(ingredient);
    }

    @Override
    public Flowable<Pair<CompleteMeal, Integer>> getAllCompleteMeals() {
        return mealsRemoteDataSource.getAllCompleteMeals();
    }
}
