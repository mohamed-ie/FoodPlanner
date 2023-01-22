package com.soha.foodplanner.data.mapper;

import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.data.model.MinMeal;
import com.soha.foodplanner.data.remote.dto.min_meal.MinMealDto;
import com.soha.foodplanner.data.remote.dto.min_meal.MinMealsItem;

import java.util.ArrayList;
import java.util.List;

public class MinMealMapper implements Mapper<MinMealDto, List<MinMeal>> {
    @Override
    public List<MinMeal> map(MinMealDto from) {
        List<MinMeal> meals = new ArrayList<>();
        for (MinMealsItem minMealsItem : from.getMeals()) {
            meals.add(new MinMeal(minMealsItem.getStrMeal()
                    , minMealsItem.getIdMeal()
                    ,  Constants.THE_MEAL_DB_IMAGES_PREVIEW+minMealsItem.getStrMealThumb() +".png"));
        }
        return meals;
    }
}
