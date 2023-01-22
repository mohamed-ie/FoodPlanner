package com.soha.foodplanner.data.remote.dto.category;

import com.google.gson.annotations.SerializedName;

public class CategoryItem {

	@SerializedName("strCategory")
	private String strCategory;

	public String getStrCategory(){
		return strCategory;
	}
}