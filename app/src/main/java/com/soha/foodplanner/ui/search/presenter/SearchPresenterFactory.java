package com.soha.foodplanner.ui.search.presenter;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.repository.search.SearchRepository;

public class SearchPresenterFactory implements Factory<SearchPresenter> {
    private final SearchRepository searchRepository;
    private final SearchPresenterListener searchPresenterListener;

    public SearchPresenterFactory(SearchRepository searchRepository, SearchPresenterListener searchPresenterListener) {
        this.searchRepository = searchRepository;
        this.searchPresenterListener = searchPresenterListener;
    }

    @Override
    public SearchPresenter create() {
        return new SearchPresenterImpl(searchRepository, searchPresenterListener);
    }
}
