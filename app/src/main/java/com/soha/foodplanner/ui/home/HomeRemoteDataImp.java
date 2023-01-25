package com.soha.foodplanner.ui.home;

import com.soha.foodplanner.data.mapper.Mapper;
import com.soha.foodplanner.data.model.MinIngredient;
import com.soha.foodplanner.data.remote.dto.MealDto;
import com.soha.foodplanner.data.remote.dto.area.AreaDto;
import com.soha.foodplanner.data.remote.dto.category.CategoryDto;
import com.soha.foodplanner.data.remote.dto.ingredient.IngredientDto;
import com.soha.foodplanner.data.remote.search.SearchRemoteDataSourceListener;
import com.soha.foodplanner.data.remote.webservice.TheMealDBWebService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeRemoteDataImp implements HomeRemoteData{
    private final List<Disposable> disposables = new ArrayList<>();
    private final TheMealDBWebService theMealDBWebService;
    private final Mapper<MealDto, List<String>> mapper;
    private final Mapper<AreaDto, List<String>> areaMapper;

    private final Mapper<CategoryDto, List<String>> categoryMapper;

    private final Mapper<IngredientDto, List<MinIngredient>> ingredientMapper;
    private final SearchRemoteDataSourceListener searchRemoteDataSourceListener;


    public HomeRemoteDataImp(TheMealDBWebService theMealDBWebService, Mapper<MealDto, List<String>> mapper, Mapper<AreaDto, List<String>> areaMapper, Mapper<CategoryDto, List<String>> categoryMapper, Mapper<IngredientDto, List<MinIngredient>> ingredientMapper, SearchRemoteDataSourceListener searchRemoteDataSourceListener) {
        this.theMealDBWebService = theMealDBWebService;
        this.mapper = mapper;
        this.areaMapper = areaMapper;
        this.categoryMapper = categoryMapper;
        this.ingredientMapper = ingredientMapper;
        this.searchRemoteDataSourceListener = searchRemoteDataSourceListener;
    }

    @Override
    public void closeAll() {
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
    }

    @Override
    public void getAllMealsByFirstLetter(char c) {
        theMealDBWebService
                .getAllMealsByFirstLetter(c)
                .subscribeOn(Schedulers.io())
                .map(mapper::map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposables.add(d);
                        searchRemoteDataSourceListener.onLoading();
                    }

                    @Override
                    public void onSuccess(@NonNull List<String> strings) {
                        searchRemoteDataSourceListener.onSearchSuccess(strings);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        searchRemoteDataSourceListener.onFailed(e.getMessage());
                    }
                });
    }

    @Override
    public void getAreas() {
        theMealDBWebService
                .getAllAreas()
                .subscribeOn(Schedulers.io())
                .map(areaMapper::map)
                .cache()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        searchRemoteDataSourceListener.onLoading();
                    }

                    @Override
                    public void onSuccess(@NonNull List<String> strings) {
                        searchRemoteDataSourceListener.onGetAllAreasSuccess(strings);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        searchRemoteDataSourceListener.onFailed(e.getMessage());
                    }
                });
    }

    @Override
    public void getAllIngredients() {
        theMealDBWebService
                .getAllIngredients()
                .subscribeOn(Schedulers.io())
                .map(ingredientMapper::map)
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(new SingleObserver<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposables.add(d);
                        searchRemoteDataSourceListener.onLoading();
                    }

                    @Override
                    public void onSuccess(@NonNull List<MinIngredient> ingredients) {
                        searchRemoteDataSourceListener.onGetAllIngredientSuccess(ingredients);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        searchRemoteDataSourceListener.onFailed(e.getMessage());
                    }
                });
    }

    @Override
    public void getAllCategories() {
        theMealDBWebService
                .getAllCategories()
                .subscribeOn(Schedulers.io())
                .map(categoryMapper::map)
                .cache()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposables.add(d);
                        searchRemoteDataSourceListener.onLoading();
                    }

                    @Override
                    public void onSuccess(@NonNull List<String> categories) {
                        searchRemoteDataSourceListener.onGetAllCategoriesSuccess(categories);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        searchRemoteDataSourceListener.onFailed(e.getMessage());
                    }
                });
    }
}
