package com.soha.foodplanner.data.local.model;

public class CompleteIngredient {
    private final String name;
    private final String thumbnailUrl;
    private final String measure;

    public CompleteIngredient(String name, String thumbnailUrl, String measure) {
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getMeasure() {
        return measure;
    }
}
