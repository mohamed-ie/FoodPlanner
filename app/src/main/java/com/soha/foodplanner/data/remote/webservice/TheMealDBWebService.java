package com.soha.foodplanner.data.remote.webservice;

import com.soha.foodplanner.data.remote.dto.area.AreaDto;
import com.soha.foodplanner.data.remote.dto.category.CategoryDto;
import com.soha.foodplanner.data.remote.dto.ingredient.IngredientDto;
import com.soha.foodplanner.data.remote.dto.min_meal.MinMealDto;
import com.soha.foodplanner.data.remote.dto.MealDto;

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
}
