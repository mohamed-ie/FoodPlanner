package com.soha.foodplanner.data.local.model;

public class User {
    private String name;
    private String email;
    private String photoUri;

    public User(String name, String email, String photoUri) {
        this.name = name;
        this.email = email;
        this.photoUri = photoUri;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
