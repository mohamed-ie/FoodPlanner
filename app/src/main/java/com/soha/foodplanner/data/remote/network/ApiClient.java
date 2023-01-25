package com.soha.foodplanner.data.remote.network;

import android.content.Context;

import com.soha.foodplanner.data.local.Meal;

import java.util.ArrayList;
import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static String url="https://www.themealdb.com/api/json/v1/1/";
    public static final String TAG="TAG";
    private List<Meal> meals=new ArrayList<>();
    private final Context context;
    private NetworkDelegate networkDelegate;
    private static ApiClient apiClient=null;
    private ApiClient(NetworkDelegate networkDelegate, Context context) {
        this.networkDelegate = networkDelegate;
        this.context = context;
    }

    private Callback call=new Callback<List<MealsItem>>() {
        @Override
        public void onResponse(Call<List<MealsItem>> call, Response<List<MealsItem>> response) {
            if (response.isSuccessful()){
                networkDelegate.onSuccessResult(response.body());
            }
        }


        @Override
        public void onFailure(Call<List<MealsItem>> call, Throwable t) {
            networkDelegate.onFailureResult(t.getMessage());
        }
    };



    private static Retrofit retrofit;
    public static Retrofit getClient(){

        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();


        }
        return retrofit;
    }
}
