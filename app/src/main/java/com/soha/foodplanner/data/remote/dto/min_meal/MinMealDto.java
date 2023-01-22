package com.soha.foodplanner.data.remote.dto.min_meal;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MinMealDto{

	@SerializedName("meals")
	private List<MinMealsItem> meals;

	public List<MinMealsItem> getMeals(){
		return meals;
	}
}