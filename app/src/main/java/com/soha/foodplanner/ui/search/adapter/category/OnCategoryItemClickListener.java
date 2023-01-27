package com.soha.foodplanner.ui.search.adapter.category;

public interface OnCategoryItemClickListener {
    default void onCategoryClick(String category) {
    }

    default void onCategoryClick(String category, boolean add) {
    }
}
