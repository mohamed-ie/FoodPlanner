package com.soha.foodplanner.data.model;

public class MinIngredient {
    private final String name;
    private final String thumbnailUrl;

    public MinIngredient(String name, String thumbnailUrl) {
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getName() {
        return name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
