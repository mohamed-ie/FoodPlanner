package com.soha.foodplanner.data.data_source.remote.webservice;

import com.soha.foodplanner.common.Constants;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Webservice {
    private static Webservice instance;
    private final Retrofit.Builder builder;
    private TheMealDBWebService theMealDBWebService;

    private Webservice() {
        builder = new Retrofit
                .Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
    }

    public static synchronized Webservice getInstance() {
        if (instance == null)
            instance = new Webservice();
        return instance;
    }

    public TheMealDBWebService getTheMealDBWebService() {
        if (theMealDBWebService == null)
            theMealDBWebService = builder
                    .baseUrl(Constants.BASE_URL_THE_MEAL_DB)
                    .build()
                    .create(TheMealDBWebService.class);
        return theMealDBWebService;
    }
}
