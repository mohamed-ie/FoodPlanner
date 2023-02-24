package com.soha.foodplanner.data.data_source.remote.webservice;


import com.soha.foodplanner.data.dto.area.AreaDto;
import com.soha.foodplanner.data.dto.category.CategoryDto;
import com.soha.foodplanner.data.dto.ingredient.IngredientDto;
import com.soha.foodplanner.data.dto.meal.MealDto;
import com.soha.foodplanner.data.dto.min_meal.MinMealDto;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMealDBWebService {
    @GET("list.php?c=list")
    Single<CategoryDto> getAllCategories();

    @GET("random.php")
    Single<MealDto> getRandomMeal();

    @GET("search.php")
    Single<MealDto> getAllMealsByFirstLetter(@Query("f") char letter);

    @GET("list.php?a=list")
    Single<AreaDto> getAllAreas();

    @GET("filter.php")
    Single<MinMealDto> getAreaMeals(@Query("a") String area);

    @GET("list.php?i=list")
    Single<IngredientDto> getAllIngredients();

    @GET("filter.php")
    Single<MinMealDto> getMealsByCategory(@Query("c") String categoryMeal);

    @GET("lookup.php")
    Single<MealDto> getMealDetailsById(@Query("i") long mealId);

    @GET("filter.php")
    Single<MinMealDto> getAllMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<MinMealDto> getAllMealsByArea(@Query("a") String area);

    @GET("filter.php")
    Single<MinMealDto> getAllMealsByIngredient(@Query("i") String ingredient);

}