package com.soha.foodplanner.data.remote.home;

import com.soha.foodplanner.data.remote.dto.category.CategoryDto;

public interface HomeRemoteDataSource {
    void getRandomMeals(int count);
    CategoryDto getCategories();
}
