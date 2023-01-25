package com.soha.foodplanner.data.mapper;

import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.dto.meal.MealDto;
import com.soha.foodplanner.data.dto.area.AreaDto;
import com.soha.foodplanner.data.dto.category.CategoryDto;
import com.soha.foodplanner.data.dto.ingredient.IngredientDto;
import com.soha.foodplanner.data.dto.min_meal.MinMealDto;

import java.util.List;

public interface MealMapper {
    List<String> map(MealDto from);

    CompleteMeal mapToCompleteMeal(MealDto from);

    List<MinIngredient> map(IngredientDto from);

    List<MinMeal> map(MinMealDto from);

    List<String> map(CategoryDto from);

    List<String> map(AreaDto from);
}
