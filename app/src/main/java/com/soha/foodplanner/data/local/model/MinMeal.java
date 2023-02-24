package com.soha.foodplanner.data.local.model;

import java.util.List;

public class MinMeal {
    private final String name;
    private final long id;
    private final String thumbnailUrl;
    
    private final List<CompleteIngredient> completeIngredients;

    public List<CompleteIngredient> getCompleteIngredients() {
        return completeIngredients;
    }

    private boolean isFavoured;

    public MinMeal(String name, long id, String thumbnailUrl, List<CompleteIngredient> completeIngredients) {
        this.name = name;
        this.id = id;
        this.thumbnailUrl = thumbnailUrl;
        this.completeIngredients = completeIngredients;
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
