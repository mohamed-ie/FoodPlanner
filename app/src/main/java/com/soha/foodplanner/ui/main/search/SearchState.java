package com.soha.foodplanner.ui.main.search;

import android.util.Pair;

import com.soha.foodplanner.data.local.model.MinIngredient;

import java.util.ArrayList;
import java.util.List;

public class SearchState {
    private List<Pair<Long, String>> search;
    private List<String> categories;
    private List<String> areas;

    private List<MinIngredient> minIngredients;

    public SearchState() {
        search = new ArrayList<>();
    }

    public List<MinIngredient> getMinIngredients() {
        return minIngredients;
    }

    public void setMinIngredients(List<MinIngredient> minIngredients) {
        this.minIngredients = minIngredients;
    }

    public List<Pair<Long, String>> getSearch() {
        return search;
    }

    public void setSearch(List<Pair<Long, String>> search) {
        this.search = search;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getAreas() {
        return areas;
    }

    public void setAreas(List<String> areas) {
        this.areas = areas;
    }
}
