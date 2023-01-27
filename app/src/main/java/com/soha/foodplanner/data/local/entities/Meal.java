package com.soha.foodplanner.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal")
public class Meal {
    @PrimaryKey
    private long id;
    private String name;
    private  String category;
    private  String area;
    private  String instructions;
    private  String photoUri;
    private  String videoUri;



    public Meal(long id, String name, String category, String area, String instructions, String photoUri, String videoUri) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.photoUri = photoUri;
        this.videoUri = videoUri;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }
}
