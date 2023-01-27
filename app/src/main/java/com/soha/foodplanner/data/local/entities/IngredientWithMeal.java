package com.soha.foodplanner.data.local.entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class IngredientWithMeal {

    @Embedded
    public Meal meal;
    @Relation(parentColumn = "id"
            , entityColumn = "name"
            , associateBy = @Junction(MealIngredientsRef.class)
    )
    public List<Ingredient> ingredients;
}
