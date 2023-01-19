package com.soha.foodplanner.data.remote.webservice;

import com.soha.foodplanner.data.remote.dto.category.CategoriesDto;
import com.soha.foodplanner.data.remote.dto.meal.RandomMealDto;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface TheMealDBWebService {
    @GET("list.php?c=list")
    Single<CategoriesDto> getAllCategories();

    @GET("random.php")
    Single<RandomMealDto> getRandomMeal();
}
