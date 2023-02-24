package com.soha.foodplanner.data.mapper;

import android.util.Pair;

import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.data.local.entities.Meal;
import com.soha.foodplanner.data.local.model.CompleteIngredient;
import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.dto.meal.MealDto;
import com.soha.foodplanner.data.dto.meal.MealItem;
import com.soha.foodplanner.data.dto.area.AreaDto;
import com.soha.foodplanner.data.dto.area.AreaItem;
import com.soha.foodplanner.data.dto.category.CategoryDto;
import com.soha.foodplanner.data.dto.category.CategoryItem;
import com.soha.foodplanner.data.dto.ingredient.IngredientDto;
import com.soha.foodplanner.data.dto.ingredient.IngredientItem;
import com.soha.foodplanner.data.dto.min_meal.MinMealDto;
import com.soha.foodplanner.data.dto.min_meal.MinMealsItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealMapperImpl implements MealMapper {
    @SuppressWarnings("FieldCanBeLocal")
    private final String FIELD_INGREDIENT = "strIngredient";
    @SuppressWarnings("FieldCanBeLocal")
    private final String FIELD_MEASURE = "strMeasure";


    @Override
    public CompleteMeal mapToCompleteMeal(MealDto from) {
        MealItem mealsItem = from.getMeals().get(0);
        Meal meal = new Meal(mealsItem.getIdMeal(),
                mealsItem.getStrMeal(),
                mealsItem.getStrCategory(),
                mealsItem.getStrArea(),
                mealsItem.getStrInstructions(),
                mealsItem.getStrMealThumb(),
                mealsItem.getStrYoutube());
        String[] splitLink = mealsItem.getStrYoutube().split("=");
        if (splitLink.length > 1)
            meal.setVideoId(splitLink[1]);
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

    @Override
    public List<String> map(MealDto from) {
        List<String> names = new ArrayList<>();
        for (MealItem mealsItem : from.getMeals()) {
            names.add(mealsItem.getStrMeal());
        }
        return names;
    }

    @Override
    public List<Pair<Long, String>> mapToSearch(MealDto from) {
        List<Pair<Long, String>> names = new ArrayList<>();
        for (MealItem mealsItem : from.getMeals()) {
            names.add(new Pair<>(mealsItem.getIdMeal(), mealsItem.getStrMeal()));
        }
        return names;
    }

    @Override
    public List<MinIngredient> map(IngredientDto from) {
        List<MinIngredient> ingredients = new ArrayList<>();
        for (IngredientItem ingredientItem : from.getMeals()) {
            ingredients.add(new MinIngredient(ingredientItem.getStrIngredient(),
//                    String.format(Constants.INGREDIENT_THUMBNAIL_PREVIEW_URL,
                    String.format(Constants.INGREDIENT_THUMBNAIL_URL,
                            ingredientItem.getStrIngredient())));
        }
        return ingredients;
    }

    @Override
    public List<MinMeal> map(MinMealDto from) {
        List<MinMeal> meals = new ArrayList<>();
        for (MinMealsItem minMealsItem : from.getMeals()) {
            meals.add(new MinMeal(minMealsItem.getStrMeal()
                    , minMealsItem.getIdMeal()
                    , minMealsItem.getStrMealThumb(), new ArrayList<>()));
        }
        return meals;
    }

    @Override
    public List<String> map(CategoryDto from) {
        return from
                .getMeals()
                .stream()
                .map(CategoryItem::getStrCategory)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> map(AreaDto from) {
        List<String> strings = new ArrayList<>();
        for (AreaItem areaItem : from.getMeals())
            strings.add(areaItem.getStrArea());
        return strings;
    }

    @Override
    public MinMeal mapToMinMeal(MealDto from) {
        CompleteMeal completeMeal = mapToCompleteMeal(from);
        return new MinMeal(completeMeal.getMeal().getName(), completeMeal.getMeal().getId(), completeMeal.getMeal().getPhotoUri(), completeMeal.getIngredients());
    }

}
