package com.soha.foodplanner.data.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.soha.foodplanner.data.data_source.remote.webservice.Webservice;
import com.soha.foodplanner.data.local.AppDatabase;
import com.soha.foodplanner.data.local.FavouriteMeals;
import com.soha.foodplanner.data.local.FavouriteMealsWithMeal;
import com.soha.foodplanner.data.local.Ingredient;
import com.soha.foodplanner.data.local.Meal;
import com.soha.foodplanner.data.local.MealDAO;
import com.soha.foodplanner.data.local.MealIngredientsRef;
import com.soha.foodplanner.data.local.PlanedMealWithMeal;
import com.soha.foodplanner.data.local.PlannedMeals;
import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.mapper.MealMapperImpl;
import com.soha.foodplanner.ui.local_details.presenter.LocalDetailsListener;
import com.soha.foodplanner.utils.InternalStorageUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repository {
    private Context context;
    private MealDAO mealDAO;

    public Repository(Context context) {
        this.context = context;
        AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());
        mealDAO = db.mealDAO();

    }

    @SuppressLint("CheckResult")
    public Meal selectMealById(String id, LocalDetailsListener localDetailsListener, View view){
        final Meal[] mealDetails = new Meal[1];
        mealDAO.FindMealById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Meal>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Meal meal) {
                        mealDetails[0]=meal;
                        localDetailsListener.setLocalValues(meal,view);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
        return mealDetails[0];
    }

    @SuppressLint("CheckResult")
    public void deleteFavMeal(FavouriteMealsWithMeal mealFav) {

        mealDAO.deleteFavouriteMeal(mealFav.getMeal().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }




//    public void deletePlannedMeal(PlanedMealWithMeal meal,CompletableObserver completableObserver){
//        mealDAO.deletePlanMeal(meal).subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(completableObserver);
//
//    }


    public Flowable<List<FavouriteMealsWithMeal>> getFavMeal() {
        return mealDAO.getFavouriteMealsWithMeal();

    }

    public Flowable<List<PlanedMealWithMeal>> getPlanedMeal() {
        return mealDAO.getPlanedMealWithMeal();
    }


    @SuppressLint("CheckResult")
    public void insertFavMeal(MinMeal mealFav) {
        Webservice.getInstance().getTheMealDBWebService().getMealDetailsById(Long.parseLong(mealFav.getId()))
                .subscribeOn(Schedulers.io())
                .map(t -> new MealMapperImpl().mapToCompleteMeal(t))
                .subscribe(new Consumer<CompleteMeal>() {
                    @Override
                    public void accept(CompleteMeal completeMeal) throws Throwable {
                        mealDAO.insertMeal(completeMeal.getMeal()).subscribeOn(Schedulers.io()).subscribe();

                        //TODO
                        completeMeal.getIngredients().forEach(completeIngredient -> {
                            Ingredient ingredient = new Ingredient();
                            ingredient.setName(completeIngredient.getName());
                            Glide.with(context)
                                    .asBitmap()
                                    .load(completeIngredient.getThumbnailUrl())
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            String s=InternalStorageUtils.saveImage(context, resource, completeIngredient.getName());
                                            ingredient.setPhotoUri(s);
                                            mealDAO.insertIngredients(ingredient).subscribeOn(Schedulers.io()).subscribe();
                                            mealDAO.insertMealIngredientsRef(new MealIngredientsRef(Long.parseLong(mealFav.getId()), completeIngredient.getName(), completeIngredient.getMeasure())).subscribeOn(Schedulers.io()).subscribe();
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {

                                        }
                                    });

                        });
                    }
                });
        mealDAO.insertFavMeal(new FavouriteMeals(Long.parseLong(mealFav.getId())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }


    @SuppressLint("CheckResult")
    public void insertPlanMeal(MinMeal mealPlan,long date,String mealTime) {
        Webservice.getInstance().getTheMealDBWebService().getMealDetailsById(Long.parseLong(mealPlan.getId()))
                .subscribeOn(Schedulers.io())
                .map(t -> new MealMapperImpl().mapToCompleteMeal(t))
                .subscribe(new Consumer<CompleteMeal>() {
                    @Override
                    public void accept(CompleteMeal completeMeal) throws Throwable {
                        mealDAO.insertMeal(completeMeal.getMeal()).subscribeOn(Schedulers.io()).subscribe();
                        completeMeal.getIngredients().forEach(completeIngredient -> {
                            Ingredient ingredient = new Ingredient();
                            ingredient.setName(completeIngredient.getName());
                            Glide.with(context)
                                    .asBitmap()
                                    .load(completeIngredient.getThumbnailUrl())
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            String s=InternalStorageUtils.saveImage(context, resource, completeIngredient.getName());
                                            ingredient.setPhotoUri(s);
                                            System.out.println(s);
                                            mealDAO.insertIngredients(ingredient).subscribeOn(Schedulers.io()).subscribe();
                                            mealDAO.insertMealIngredientsRef(new MealIngredientsRef(Long.parseLong(mealPlan.getId()), completeIngredient.getName(), completeIngredient.getMeasure())).subscribeOn(Schedulers.io()).subscribe();
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {

                                        }
                                    });

                        });
                    }
                });
        mealDAO.insertPlannedMeal(new PlannedMeals(Long.parseLong(mealPlan.getId()),date,mealTime))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }


}
