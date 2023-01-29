package com.soha.foodplanner.ui.search;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SearchState {
    private List<Pair<Long,String>> search;

    public SearchState() {
        search = new ArrayList<>();
    }

    public List<Pair<Long, String>> getSearch() {
        return search;
    }

    public void setSearch(List<Pair<Long, String>> search) {
        this.search = search;
    }

}
