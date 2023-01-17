package com.soha.foodplanner.data.local;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FavMeal{

	@SerializedName("meals")
	private List<MealsItem> meals;

	public void setMeals(List<MealsItem> meals){
		this.meals = meals;
	}

	public List<MealsItem> getMeals(){
		return meals;
	}
}