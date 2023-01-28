package com.soha.foodplanner.data.data_source.remote.meals;

import com.soha.foodplanner.data.mapper.MealMapper;
import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.data_source.remote.webservice.TheMealDBWebService;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsRemoteDataSourceImpl implements MealsRemoteDataSource {
    private final TheMealDBWebService theMealDBWebService;
    private  MealMapper mapper;

    public MealsRemoteDataSourceImpl(TheMealDBWebService theMealDBWebService, MealMapper mapper) {
        this.theMealDBWebService = theMealDBWebService;
        this.mapper = mapper;
    }

    @NonNull
    public Single<List<MinMeal>> getMealsByCategory(String category) {
        return theMealDBWebService
                .getAllMealsByCategory(category)
                .subscribeOn(Schedulers.io())
                .map(mapper::map)
                .doOnSubscribe(disposables::add);
    }

    @Override
    public Single<List<MinMeal>> getMealsByArea(String area) {
        return theMealDBWebService
                .getAllMealsByArea(area)
                .subscribeOn(Schedulers.io())
                .map(mapper::map)
                .doOnSubscribe(disposables::add);
    }

    @Override
    public Single<List<MinMeal>> getAreaMeals(String area) {
        return theMealDBWebService.getAreaMeals(area)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposables::add)
                .map(mapper::map);
    }

    @Override
    public Single<List<String>> getAllMealsByFirstLetter(char c) {
        return theMealDBWebService.getAllMealsByFirstLetter(c)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposables::add)
                .map(mapper::map);
    }

    @Override
    public Single<List<String>> getAreas() {
        return theMealDBWebService.getAllAreas()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposables::add)
                .map(mapper::map);
    }

    @Override
    public Single<List<MinIngredient>> getAllIngredients() {
        return theMealDBWebService.getAllIngredients()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposables::add)
                .map(mapper::map);
    }

    @Override
    public Single<List<String>> getAllCategories() {
        return theMealDBWebService.getAllCategories()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposables::add)
                .map(mapper::map);
    }
}
