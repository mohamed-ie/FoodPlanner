package com.soha.foodplanner.data.model;

public class CompleteIngredient {
    private final String ingredient;
    private final String thumbnailUrl;
    private final String measure;

    public CompleteIngredient(String ingredient, String thumbnailUrl, String measure) {
        this.ingredient = ingredient;
        this.thumbnailUrl = thumbnailUrl;
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getMeasure() {
        return measure;
    }
}
