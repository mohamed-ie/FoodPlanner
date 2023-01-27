package com.soha.foodplanner.data.local.entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class MealWithIngredient {
    @Embedded
    public Ingredient ingredient;
    @Relation(parentColumn = "name"
            , entityColumn = "id"
            , associateBy = @Junction(MealIngredientsRef.class)
    )
    public List<Meal> meals;

}
