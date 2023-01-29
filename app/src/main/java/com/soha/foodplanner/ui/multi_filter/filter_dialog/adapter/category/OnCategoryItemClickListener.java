package com.soha.foodplanner.ui.multi_filter.filter_dialog.adapter.category;

public interface OnCategoryItemClickListener {
    default void onCategoryClick(String category) {
    }

    default void onCategoryClick(String category, boolean add) {
    }
}
