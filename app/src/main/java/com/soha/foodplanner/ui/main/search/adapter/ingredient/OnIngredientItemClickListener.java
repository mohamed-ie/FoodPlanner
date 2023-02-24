package com.soha.foodplanner.ui.main.search.adapter.ingredient;

public interface OnIngredientItemClickListener {
    default void onIngredientClick(String name){}
    default void onIngredientClick(String name,boolean add){}
}
