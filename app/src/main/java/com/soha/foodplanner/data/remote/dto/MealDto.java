package com.soha.foodplanner.data.remote.dto;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MealDto {

	@SerializedName("meals")
	private List<MealsItem> meals;

	public List<MealsItem> getMeals(){
		return meals;
	}
}