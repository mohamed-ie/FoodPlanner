package com.soha.foodplanner.data.mapper;

import androidx.annotation.NonNull;

import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.data.local.Meal;
import com.soha.foodplanner.data.model.CompleteIngredient;
import com.soha.foodplanner.data.model.CompleteMeal;
import com.soha.foodplanner.data.remote.dto.MealDto;
import com.soha.foodplanner.data.remote.dto.MealsItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MealMapper implements Mapper<MealDto, CompleteMeal> {
    @SuppressWarnings("FieldCanBeLocal")
    private final String FIELD_INGREDIENT = "strIngredient";
    @SuppressWarnings("FieldCanBeLocal")
    private final String FIELD_MEASURE = "strMeasure";


    @Override
    public CompleteMeal map(@NonNull MealDto from) {
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
        for (Field field : fields) {
            if (field.getName().startsWith(FIELD_INGREDIENT)) {
                try {
                    field.setAccessible(true);
                    String ingredient = (String) field.get(mealsItem);

                    Field measureField = mealsItem
                            .getClass()
                            .getDeclaredField(FIELD_MEASURE + field.getName().substring(FIELD_INGREDIENT.length()));
                    measureField.setAccessible(true);
                    String measure = (String) measureField.get(mealsItem);

                    if (ingredient != null && !ingredient.isEmpty())
                        ingredients.add(new CompleteIngredient(
                                ingredient,
                                String.format(Constants.INGREDIENT_THUMBNAIL_PREVIEW_URL, ingredient),
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
