package com.soha.foodplanner.data.repository.meals;

import android.graphics.Bitmap;

import com.bumptech.glide.RequestBuilder;

import java.util.concurrent.ExecutionException;

public class GlideImageDownloader {

    private final RequestBuilder<Bitmap> glideRequestBuilder;

    public GlideImageDownloader(RequestBuilder<Bitmap> glideRequestBuilder) {
        this.glideRequestBuilder = glideRequestBuilder;
    }

    public Bitmap download(String url) throws ExecutionException, InterruptedException {
        return glideRequestBuilder.load(url)
                .submit().get();
    }
}
