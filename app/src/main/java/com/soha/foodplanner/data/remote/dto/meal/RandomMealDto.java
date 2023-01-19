package com.soha.foodplanner.data.remote.dto.meal;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RandomMealDto{

	@SerializedName("meals")
	private List<MealsItem> meals;

	public List<MealsItem> getMeals(){
		return meals;
	}
}