package com.soha.foodplanner.data.remote.network;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("filter.php")
    Single<List<MealsItem>> getMeals(@Query("c")String category);


}
