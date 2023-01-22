package com.soha.foodplanner.data.mapper;

import com.soha.foodplanner.data.remote.dto.MealDto;
import com.soha.foodplanner.data.remote.dto.MealsItem;

import java.util.ArrayList;
import java.util.List;

public class MealNameMapper implements Mapper<MealDto, List<String>> {

    @Override
    public List<String> map(MealDto from) {
        List<String> names = new ArrayList<>();
        for (MealsItem mealsItem : from.getMeals()) {
            names.add(mealsItem.getStrMeal());
        }
        return names;
    }
}
