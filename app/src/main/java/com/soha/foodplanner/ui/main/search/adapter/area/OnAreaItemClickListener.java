package com.soha.foodplanner.ui.main.search.adapter.area;

public interface OnAreaItemClickListener {
    default void onAreaItemClick(String area){}
    default void onAreaClick(String name, boolean add){};
}
