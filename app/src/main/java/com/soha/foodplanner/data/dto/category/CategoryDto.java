package com.soha.foodplanner.data.dto.category;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CategoryDto{

	@SerializedName("meals")
	private List<CategoryItem> meals;

	public List<CategoryItem> getMeals(){
		return meals;
	}
}