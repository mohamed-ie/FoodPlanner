package com.soha.foodplanner.data.remote.webservice;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Webservice {
    private static Webservice instance;
    private final Retrofit.Builder builder;

    private Webservice() {
        builder = new Retrofit
                .Builder()
                .client(new OkHttpClient())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
    }

    public static synchronized Webservice getInstance() {
        if (instance == null)
            instance = new Webservice();
        return instance;
    }

    public <T> T build(String baseUrl, Class<T> clazz) {
        return builder.baseUrl(baseUrl).build().create(clazz);
    }
}
