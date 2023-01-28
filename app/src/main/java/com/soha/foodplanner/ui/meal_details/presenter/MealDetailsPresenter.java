package com.soha.foodplanner.ui.meal_details.presenter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import com.soha.foodplanner.data.data_source.remote.meals.MealsRemoteDataSource;
import com.soha.foodplanner.data.data_source.remote.meals.MealsRemoteDataSourceImpl;
import com.soha.foodplanner.data.data_source.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.dto.area.AreaDto;
import com.soha.foodplanner.data.dto.category.CategoryDto;
import com.soha.foodplanner.data.dto.ingredient.IngredientDto;
import com.soha.foodplanner.data.dto.meal.MealDto;
import com.soha.foodplanner.data.dto.min_meal.MinMealDto;
import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.mapper.MealMapper;
import com.soha.foodplanner.data.mapper.MealMapperImpl;
import com.soha.foodplanner.ui.meal_details.IngredientAdapter;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenter {
    public MealDetailsPresenter(TheMealDBWebService theMealDBWebService, MealDetailsListener mealDetailsListener,View view) {
        this.theMealDBWebService = theMealDBWebService;
        this.mealDetailsListener = mealDetailsListener;
        this.view=view;
        mealsRemoteDataSource=new MealsRemoteDataSourceImpl(theMealDBWebService,new MealMapperImpl());

    }

    TheMealDBWebService theMealDBWebService;
    MealsRemoteDataSourceImpl mealsRemoteDataSource;

    View view;
    MealDetailsListener mealDetailsListener;

    @SuppressLint("CheckResult")
    public void getDetails(String mealIdStr){
        MealMapper mealMapper=new MealMapperImpl();
        Single<MealDto> single = theMealDBWebService.getMealDetailsById(Long.parseLong(mealIdStr));
        single
                .subscribeOn(Schedulers.io())
                .map(mealMapper::mapToCompleteMeal)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(e-> Log.e("error",e.getMessage()))
                .subscribe(e->
                        {   e.getIngredients();
                            //setMealValues(e,view);
                            mealDetailsListener.setValues(e.getMeal(),view);
                            mealDetailsListener.setIngredients(e.getIngredients(),view);
                        }
                );
        Single<List<MinIngredient>> singleIng= mealsRemoteDataSource.getAllIngredients();
        singleIng.subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingr->{

                    Log.i("TAG", "getDetails: "+ingr.get(0).getName());
                },e->{e.printStackTrace();});

    }

}
