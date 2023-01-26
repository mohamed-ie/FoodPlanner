package com.soha.foodplanner.data.repository;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

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
import com.soha.foodplanner.data.local.MealDAO;
import com.soha.foodplanner.data.local.MealIngredientsRef;
import com.soha.foodplanner.data.local.PlanedMealWithMeal;
import com.soha.foodplanner.data.local.PlannedMeals;
import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.mapper.MealMapperImpl;
import com.soha.foodplanner.utils.InternalStorageUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
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

//    public void getAllMeals(SingleObserver<List<Meal>> singleObserver){
//        mealDAO.getAllMeals().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(singleObserver);
//    }

//    public void delete(Meal meal,CompletableObserver completableObserver){
//        mealDAO.deleteMeal(meal).subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(completableObserver);
//
//    }

    public void insert(CompleteMeal completeMeal, CompletableObserver completableObserver) {
        mealDAO.insertMeal((completeMeal.getMeal())).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completableObserver);
    }

    public Flowable<List<FavouriteMealsWithMeal>> getFavMeal() {
        return mealDAO.getFavouriteMealsWithMeal();

    }

    public Flowable<List<PlanedMealWithMeal>> getPlanedMeal() {
        return mealDAO.getPlanedMealWithMeal();
    }

    public void insertPlannedMeal(PlannedMeals plannedMeals, CompletableObserver completableObserver) {

        mealDAO.insertPlannedMeal(plannedMeals)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completableObserver);

    }

    public void insertMealIngredientsRef(List<MealIngredientsRef> mealIngredientsRefs, CompletableObserver completableObserver) {

//        mealDAO.insertMealIngredientsRef(mealIngredientsRefs)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(completableObserver);
    }

    public void insertIngredients(List<Ingredient> ingredients, CompletableObserver completableObserver) {

//        mealDAO.insertIngredients(ingredients)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(completableObserver);
    }

    public void insertFavMeal(MinMeal mealFav) {
        Webservice.getInstance().getTheMealDBWebService().getMealDetailsById(Long.parseLong(mealFav.getId()))
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


//    public void searchMealByArea(String mealArea,SingleObserver<List<Meal>> mealSingleObserver){
//
//        mealDAO.FindMealByArea(mealArea)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(mealSingleObserver);
//    }
//    public void searchMealByCategory(String mealCategory,SingleObserver<List<Meal>> singleObserver){
//        mealDAO.FindMealByCategory(mealCategory)
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(singleObserver);
//    }
}
