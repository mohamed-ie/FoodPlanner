package com.soha.foodplanner.data.remote.network;


import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Helper {

    public void start(NetworkDelegate networkDelegate){

        Retrofit apiClient= ApiClient.getClient();
        ApiInterface apiInterface=apiClient.create(ApiInterface.class);
        Single<List<MealsItem>> listSingle=apiInterface.getMeals("Seafood");
        listSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MealsItem>>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull List<MealsItem> meals) {


            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });

        /*
        call.enqueue(new Callback<Root>(){

            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Log.i("eee","onResponse");
                //Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT);
                if(response.isSuccessful()){
                    ArrayList<Product> products=response.body().getProducts();
                    networkDelegate.onSuccessResult(products);


                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Log.i("sss","onFailure");
                networkDelegate.onFailureResult("fail");
                //Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT).show();

            }
        });

         */
    }
}

