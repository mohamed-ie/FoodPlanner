package com.soha.foodplanner.data.local.model;

public class MinMeal {
    private final String name;
    private final long id;
    private final String thumbnailUrl;

    private boolean isFavoured;

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

    public boolean isFavoured() {
        return isFavoured;
    }

    public void setFavoured(boolean favoured) {
        isFavoured = favoured;
    }
}
