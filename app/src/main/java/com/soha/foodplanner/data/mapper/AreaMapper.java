package com.soha.foodplanner.data.mapper;

import com.soha.foodplanner.data.remote.dto.area.AreaDto;
import com.soha.foodplanner.data.remote.dto.area.AreaItem;

import java.util.ArrayList;
import java.util.List;

public class AreaMapper implements Mapper<AreaDto, List<String>> {
    @Override
    public List<String> map(AreaDto from) {
        List<String> strings = new ArrayList<>();
        for (AreaItem areaItem : from.getMeals())
            strings.add(areaItem.getStrArea());
        return strings;
    }
}
