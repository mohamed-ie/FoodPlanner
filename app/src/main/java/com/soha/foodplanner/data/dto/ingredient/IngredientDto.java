package com.soha.foodplanner.data.dto.ingredient;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class IngredientDto{

	@SerializedName("meals")
	private List<IngredientItem> meals;

	public List<IngredientItem> getMeals(){
		return meals;
	}
}