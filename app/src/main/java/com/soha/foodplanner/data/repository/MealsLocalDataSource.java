package com.soha.foodplanner.data.repository;

import android.annotation.SuppressLint;

import com.soha.foodplanner.data.local.AppDatabase;
import com.soha.foodplanner.data.local.entities.FavouriteMeals;
import com.soha.foodplanner.data.local.entities.FavouriteMealsWithMeal;
import com.soha.foodplanner.data.local.entities.Ingredient;
import com.soha.foodplanner.data.local.entities.Meal;
import com.soha.foodplanner.data.local.MealDAO;
import com.soha.foodplanner.data.local.entities.MealIngredientsRef;
import com.soha.foodplanner.data.local.entities.PlanedMealWithMeal;
import com.soha.foodplanner.data.local.entities.PlannedMeals;
import com.soha.foodplanner.data.local.model.CompleteIngredient;
import com.soha.foodplanner.data.local.model.CompleteMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsLocalDataSource {
    private final MealDAO mealDAO;

    public MealsLocalDataSource(AppDatabase appDatabase) {
        this.mealDAO = appDatabase.mealDAO();
    }


    public Single<Meal> selectMealById(String id) {
        return mealDAO.FindMealById(id)
                .subscribeOn(Schedulers.io());
    }

    public Completable deleteFavMeal(FavouriteMealsWithMeal mealFav) {
        return mealDAO.deleteFavouriteMeal(mealFav.getMeal().getId())
                .subscribeOn(Schedulers.io());
    }

    public Completable deletePlannedMeal(PlanedMealWithMeal planedMealWithMeal) {
        return mealDAO.deletePlannedMeal(planedMealWithMeal.getPlannedMeals().getId())
                .subscribeOn(Schedulers.io());
    }


    public Flowable<List<FavouriteMealsWithMeal>> getFavMeal() {
        return mealDAO.getFavouriteMealsWithMeal()
                .subscribeOn(Schedulers.io());
    }

    public Flowable<List<PlanedMealWithMeal>> getPlanedMeal() {
        return mealDAO.getPlanedMealWithMeal()
                .subscribeOn(Schedulers.io());
    }


    public Completable insertFavMeal(CompleteMeal completeMeal) {
        //insert favourite meal
        return insertMealWithIngredients(completeMeal)
                .ambWith(mealDAO.insertFavMeal(new FavouriteMeals(completeMeal.getMeal().getId())));

//        Webservice.getInstance()
//                .getTheMealDBWebService()
//                .getMealDetailsById(Long.parseLong(mealFav.getId()))
//                .subscribeOn(Schedulers.io())
//                .map(t -> new MealMapperImpl().mapToCompleteMeal(t))
//                .subscribe(new Consumer<CompleteMeal>() {
//                    @Override
//                    public void accept(CompleteMeal completeMeal) throws Throwable {


//
//
//
//        //TODO
//        completeMeal.getIngredients().forEach(completeIngredient -> {
//            Ingredient ingredient = new Ingredient();
//            ingredient.setName(completeIngredient.getName());
//            Glide.with(context)
//                    .asBitmap()
//                    .load(completeIngredient.getThumbnailUrl())
//                    .into(new CustomTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                            String s = InternalStorageUtils.saveImage(context, resource, completeIngredient.getName());
//                            ingredient.setPhotoUri(s);
//                        }
//
//                        @Override
//                        public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                        }
//                    });
//
//        });
//    }
//});

    }

    private Completable insertMealWithIngredients(CompleteMeal completeMeal) {
        Meal meal = completeMeal.getMeal();
        List<CompleteIngredient> completeIngredients = completeMeal.getIngredients();
        //insert meal 1st
        Completable completable = mealDAO.insertMeal(completeMeal.getMeal())
                .subscribeOn(Schedulers.io());

        for (CompleteIngredient completeIngredient : completeIngredients)
            //insert ingredient 2nd
            completable = completable.ambWith(mealDAO.insertIngredients(new Ingredient(completeIngredient.getName(), completeIngredient.getThumbnailUrl())))
                    //insert ingredient-meal relationship 3rd
                    .ambWith(mealDAO.insertMealIngredientsRef(new MealIngredientsRef(meal.getId(), completeIngredient.getName(), completeIngredient.getMeasure())));
        return completable;
    }


    @SuppressLint("CheckResult")
    public Completable insertPlanMeal(CompleteMeal completeMeal, long date, String mealTime) {
        return insertMealWithIngredients(completeMeal)
                .ambWith(mealDAO.insertPlannedMeal(new PlannedMeals(completeMeal.getMeal().getId(), date, mealTime)));
//        Webservice.getInstance().getTheMealDBWebService().getMealDetailsById(Long.parseLong(mealPlan.getId()))
//                .subscribeOn(Schedulers.io())
//                .map(t -> new MealMapperImpl().mapToCompleteMeal(t))
//                .subscribe(new Consumer<CompleteMeal>() {
//                    @Override
//                    public void accept(CompleteMeal completeMeal) throws Throwable {
//                        mealDAO.insertMeal(completeMeal.getMeal()).subscribeOn(Schedulers.io()).subscribe();
//                        completeMeal.getIngredients().forEach(completeIngredient -> {
//                            Ingredient ingredient = new Ingredient();
//                            ingredient.setName(completeIngredient.getName());
//                            Glide.with(context)
//                                    .asBitmap()
//                                    .load(completeIngredient.getThumbnailUrl())
//                                    .into(new CustomTarget<Bitmap>() {
//                                        @Override
//                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                                            String s = InternalStorageUtils.saveImage(context, resource, completeIngredient.getName());
//                                            ingredient.setPhotoUri(s);
//                                            System.out.println(s);
//                                            mealDAO.insertIngredients(ingredient).subscribeOn(Schedulers.io()).subscribe();
//                                            mealDAO.insertMealIngredientsRef(new MealIngredientsRef(Long.parseLong(mealPlan.getId()), completeIngredient.getName(), completeIngredient.getMeasure())).subscribeOn(Schedulers.io()).subscribe();
//                                        }
//
//                                        @Override
//                                        public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                                        }
//                                    });
//
//                        });
//                    }
//                });
    }

    public Flowable<Long> getAllFavouriteMealsIds() {
        return mealDAO.getAllFavouriteMealsIds().subscribeOn(Schedulers.io());
    }
}
