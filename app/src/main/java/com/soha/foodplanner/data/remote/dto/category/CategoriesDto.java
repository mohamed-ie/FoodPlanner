package com.soha.foodplanner.data.remote.dto.category;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CategoriesDto {

	@SerializedName("meals")
	private List<CategoryDto> categories;

	public List<CategoryDto> getCategories(){
		return categories;
	}
}