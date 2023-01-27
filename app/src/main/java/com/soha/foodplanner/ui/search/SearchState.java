package com.soha.foodplanner.ui.search;

import java.util.ArrayList;
import java.util.List;

public class SearchState {
    private List<String> search;

    public SearchState() {
        search = new ArrayList<>();
    }

    public List<String> getSearch() {
        return search;
    }

    public void setSearch(List<String> search) {
        this.search = search;
    }

}
