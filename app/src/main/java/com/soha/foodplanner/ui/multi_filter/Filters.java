package com.soha.foodplanner.ui.multi_filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Filters implements Serializable {
    private String category;
    private String area;
    private final List<String> ingredients;

    public Filters() {
        this.category = "";
        this.area = "";
        this.ingredients = new ArrayList<>();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}
