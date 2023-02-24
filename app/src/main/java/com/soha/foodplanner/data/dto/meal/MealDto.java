package com.soha.foodplanner.data.dto.meal;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MealDto {

	@SerializedName("meals")
	private List<MealItem> meals;

	public List<MealItem> getMeals(){
		return meals;
	}
}