package com.soha.foodplanner.data.mapper;

import com.soha.foodplanner.data.remote.dto.category.CategoryDto;
import com.soha.foodplanner.data.remote.dto.category.CategoryItem;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper implements Mapper<CategoryDto, List<String>> {

    @Override
    public List<String> map(CategoryDto from) {
        return from
                .getMeals()
                .stream()
                .map(CategoryItem::getStrCategory)
                .collect(Collectors.toList());
    }
}
