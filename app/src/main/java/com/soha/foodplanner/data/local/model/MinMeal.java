package com.soha.foodplanner.data.local.model;

public class MinMeal {
    private final String name;
    private final long id;
    private final String thumbnailUrl;

    public MinMeal(String name, long id, String thumbnailUrl) {
        this.name = name;
        this.id = id;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
