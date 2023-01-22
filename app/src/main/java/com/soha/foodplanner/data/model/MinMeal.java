package com.soha.foodplanner.data.model;

public class MinMeal {
    private final String meal;
    private final String id;
    private final String thumbnailUrl;

    public MinMeal(String meal, String id, String thumbnailUrl) {
        this.meal = meal;
        this.id = id;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getMeal() {
        return meal;
    }

    public String getId() {
        return id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
