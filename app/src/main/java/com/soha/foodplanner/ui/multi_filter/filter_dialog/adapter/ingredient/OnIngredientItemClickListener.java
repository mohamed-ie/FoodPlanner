package com.soha.foodplanner.ui.multi_filter.filter_dialog.adapter.ingredient;

public interface OnIngredientItemClickListener {
    default void onIngredientClick(String name){}
    default void onIngredientClick(String name,boolean add){}
}
