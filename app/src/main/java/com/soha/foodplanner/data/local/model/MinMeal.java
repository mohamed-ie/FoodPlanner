package com.soha.foodplanner.data.local.model;

public class MinMeal {
    private final String name;
    private final String id;
    private final String thumbnailUrl;

    public MinMeal(String name, String id, String thumbnailUrl) {
        this.name = name;
        this.id = id;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
