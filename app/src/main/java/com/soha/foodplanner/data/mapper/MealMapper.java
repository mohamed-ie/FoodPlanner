package com.soha.foodplanner.data.mapper;

import androidx.annotation.NonNull;

import com.soha.foodplanner.data.local.Meal;
import com.soha.foodplanner.data.model.CompleteIngredient;
import com.soha.foodplanner.data.model.CompleteMeal;
import com.soha.foodplanner.data.remote.dto.meal.MealsItem;
import com.soha.foodplanner.data.remote.dto.meal.RandomMealDto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MealMapper implements Mapper<RandomMealDto, CompleteMeal> {
    @SuppressWarnings("FieldCanBeLocal")
    private final String FIELD_INGREDIENT = "strIngredient";
    @SuppressWarnings("FieldCanBeLocal")
    private final String FIELD_MEASURE = "strMeasure";
    @SuppressWarnings("FieldCanBeLocal")
    private final String INGREDIENT_THUMBNAIL_URL = "https://www.themealdb.com/images/ingredients/";

    @Override
    public CompleteMeal map(@NonNull RandomMealDto from) {
        MealsItem mealsItem = from.getMeals().get(0);
        Meal meal = new Meal(Long.parseLong(mealsItem.getIdMeal()),
                mealsItem.getStrMeal(),
                mealsItem.getStrCategory(),
                mealsItem.getStrArea(),
                mealsItem.getStrInstructions(),
                mealsItem.getStrMealThumb(),
                mealsItem.getStrYoutube());

        List<CompleteIngredient> ingredients = new ArrayList<>();
        Field[] fields = mealsItem.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field.getName().startsWith(FIELD_INGREDIENT)) {
                try {
                    field.setAccessible(true);
                    String ingredient = (String) field.get(mealsItem);

                    Field measureField = mealsItem.getClass().getDeclaredField(FIELD_MEASURE + field.getName().substring(FIELD_INGREDIENT.length()));
                    measureField.setAccessible(true);
                    String measure = (String) measureField.get(mealsItem);

                    if (ingredient != null && !ingredient.isEmpty())
                        ingredients.add(new CompleteIngredient(
                                ingredient,
                                INGREDIENT_THUMBNAIL_URL + ingredient + ".png",
                                measure
                        ));
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }

        return new CompleteMeal(meal, ingredients);
    }
}
