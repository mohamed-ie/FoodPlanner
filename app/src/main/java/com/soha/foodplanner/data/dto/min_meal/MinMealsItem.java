package com.soha.foodplanner.data.dto.min_meal;

import com.google.gson.annotations.SerializedName;

public class MinMealsItem {

	@SerializedName("strMealThumb")
	private String strMealThumb;

	@SerializedName("idMeal")
	private long idMeal;

	@SerializedName("strMeal")
	private String strMeal;

	public String getStrMealThumb(){
		return strMealThumb;
	}

	public long getIdMeal(){
		return idMeal;
	}

	public String getStrMeal(){
		return strMeal;
	}
}