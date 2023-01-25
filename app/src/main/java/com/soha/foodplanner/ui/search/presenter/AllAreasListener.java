package com.soha.foodplanner.ui.search.presenter;

import java.util.List;

public interface AllAreasListener {
    void onGetAllAreasSuccess(List<String> areas);

    void onGetAllAreasLoading();

    void onGetAllAreasError(String message);
}
