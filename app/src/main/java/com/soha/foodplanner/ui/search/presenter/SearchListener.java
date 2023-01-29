package com.soha.foodplanner.ui.search.presenter;

import android.util.Pair;

import java.util.List;

public interface SearchListener {
    void onSearchSuccess(List<Pair<Long, String>> names);

    void onSearchError(String message);

    void onSearchLoading();
}
