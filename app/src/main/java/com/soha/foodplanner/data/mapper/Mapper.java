package com.soha.foodplanner.data.mapper;

public interface Mapper<F,T> {
    T map (F from);
}
