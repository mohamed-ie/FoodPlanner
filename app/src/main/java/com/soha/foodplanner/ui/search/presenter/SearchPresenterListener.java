package com.soha.foodplanner.ui.search.presenter;

import com.soha.foodplanner.data.remote.common.Failed;
import com.soha.foodplanner.data.remote.search.SearchRemoteDataSource;
import com.soha.foodplanner.data.remote.search.SearchRemoteDataSourceListener;

import java.util.List;

public interface SearchPresenterListener extends Failed , SearchRemoteDataSourceListener {
    void onSearchSuccess(List<String> names);
}
