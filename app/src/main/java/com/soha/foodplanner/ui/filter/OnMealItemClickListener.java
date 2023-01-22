package com.soha.foodplanner.ui.filter;

import com.soha.foodplanner.ui.addapters.OnItemClickListener;

public interface OnMealItemClickListener extends OnItemClickListener<String> {
    void onFavouriteClick(String id);
}
