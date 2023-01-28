package com.soha.foodplanner.data.data_source.remote.meals;

import android.util.Pair;

import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.mapper.MealMapper;
import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.data_source.remote.webservice.TheMealDBWebService;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
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

    @Override
    public Single<CompleteMeal> getMealDetailsById(long id) {
        return theMealDBWebService.getMealDetailsById(id)
                .subscribeOn(Schedulers.io())
                .map(mapper::mapToCompleteMeal);
    }

    @Override
    public Single<List<MinMeal>> getAllMealsByIngredient(String ingredient) {
        return theMealDBWebService.getAllMealsByIngredient(ingredient)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposables::add)
                .map(mapper::map);
    }

    //DO NOT DO TRY THAT AT HOME XD
    @Override
    public Flowable<Pair<CompleteMeal, Integer>> getAllCompleteMeals() {
        final int[] done = {0};
        final int[] count = {0};
        return theMealDBWebService.getAllCategories()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(categoryDto -> count[0] = categoryDto.getMeals().size())
                .toFlowable()
                .flatMap(categoryDto -> Flowable.fromIterable(categoryDto.getMeals()))
                .doOnNext(ignored -> done[0]++)
                .flatMap(categoryItem -> theMealDBWebService.getAllMealsByCategory(categoryItem.getStrCategory()).filter(t -> t.getMeals() != null).toFlowable())
                .flatMap(minMealDto -> Flowable.fromIterable(minMealDto.getMeals()))
                .flatMap(minMealsItem -> theMealDBWebService.getMealDetailsById(minMealsItem.getIdMeal()).toFlowable())
                .subscribeOn(Schedulers.computation())
                .map(mapper::mapToCompleteMeal)
                .map(completeMeal -> new Pair<>(completeMeal, Float.valueOf((done[0] * 1f / count[0]) * 100).intValue()));
    }
}
