package com.soha.foodplanner.data.mapper;

import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.data.model.MinIngredient;
import com.soha.foodplanner.data.remote.dto.ingredient.IngredientDto;
import com.soha.foodplanner.data.remote.dto.ingredient.IngredientItem;

import java.util.ArrayList;
import java.util.List;

public class MinIngredientMapper implements Mapper<IngredientDto, List<MinIngredient>> {
    @Override
    public List<MinIngredient> map(IngredientDto from) {
        List<MinIngredient> ingredients = new ArrayList<>();
        for (IngredientItem ingredientItem : from.getMeals()) {
            ingredients.add(new MinIngredient(ingredientItem.getStrIngredient(),
                    String.format(Constants.INGREDIENT_THUMBNAIL_PREVIEW_URL,
                            ingredientItem.getStrIngredient())));
        }
        return ingredients;
    }
}
