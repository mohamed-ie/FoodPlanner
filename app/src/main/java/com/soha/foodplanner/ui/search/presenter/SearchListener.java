package com.soha.foodplanner.ui.search.presenter;

import java.util.List;

public interface SearchListener {
    void onSearchSuccess(List<String> names);

    void onSearchError(String message);

    void onSearchLoading();
}
