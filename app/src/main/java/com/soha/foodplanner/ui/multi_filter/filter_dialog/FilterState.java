package com.soha.foodplanner.ui.multi_filter.filter_dialog;

import com.soha.foodplanner.ui.multi_filter.Filters;

import java.util.List;

import kotlin.Pair;

public class FilterState {
    private List<Pair<String,Boolean>> categories;
    private List<Pair<String,Boolean>> ingredients;
    private List<Pair<String,Boolean>> areas;

    private Filters filters = new Filters();

    public List<Pair<String,Boolean>> getCategories() {
        return categories;
    }

    public void setCategories(List<Pair<String,Boolean>> categories) {
        this.categories = categories;
    }

    public List<Pair<String,Boolean>> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Pair<String,Boolean>> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Pair<String,Boolean>> getAreas() {
        return areas;
    }

    public void setAreas(List<Pair<String,Boolean>> areas) {
        this.areas = areas;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        if (filters != null)
            this.filters = filters;
    }
}
