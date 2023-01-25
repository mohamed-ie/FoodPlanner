package com.soha.foodplanner.ui.search.presenter;

import java.util.List;

public interface AllCategoriesListener {
    void onGetAllCategoriesSuccess(List<String> categories);

    void onGetAllCategoriesError(String message);

    void onGetAllCategoriesLoading();
}
