package com.soha.foodplanner.data.local.model;

public class CompleteIngredient {
    private  String name;
    private  String thumbnailUrl;
    private  String measure;

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


    public void setName(String name) {
        this.name = name;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}
