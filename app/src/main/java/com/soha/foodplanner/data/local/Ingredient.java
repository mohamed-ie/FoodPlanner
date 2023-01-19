package com.soha.foodplanner.data.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "ingredient")
public class Ingredient {
    @PrimaryKey
    @NonNull
    private String name;
    private String photoUri;

    public Ingredient(String name, String photoUri) {
        this.name = name;
        this.photoUri=photoUri;

    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
