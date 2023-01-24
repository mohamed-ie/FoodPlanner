package com.soha.foodplanner.data.repository;

import android.content.Context;

import com.soha.foodplanner.data.local.AppDatabase;
import com.soha.foodplanner.data.local.FavouriteMealsWithMeal;
import com.soha.foodplanner.data.local.Ingredient;
import com.soha.foodplanner.data.local.Meal;
import com.soha.foodplanner.data.local.MealDAO;
import com.soha.foodplanner.data.local.MealIngredientsRef;
import com.soha.foodplanner.data.local.PlanedMealWithMeal;
import com.soha.foodplanner.data.local.PlannedMeals;
import com.soha.foodplanner.data.model.CompleteMeal;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repository {
    private Context context;
    private MealDAO mealDAO;

    public Repository(Context context){
        this.context=context;
        AppDatabase db= AppDatabase.getInstance(context.getApplicationContext());
        mealDAO=db.mealDAO();

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

    public Flowable<List<FavouriteMealsWithMeal>> getFavMeal(){
        return mealDAO.getFavouriteMealsWithMeal();

    }

    public Flowable<List<PlanedMealWithMeal>> getPlanedMeal(){
        return mealDAO.getPlanedMealWithMeal();
    }

    public void insertPlannedMeal(PlannedMeals plannedMeals,CompletableObserver completableObserver){

        mealDAO.insertPlannedMeal(plannedMeals)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completableObserver);

    }

    public void insertMealIngredientsRef(List<MealIngredientsRef> mealIngredientsRefs, CompletableObserver completableObserver){

        mealDAO.insertMealIngredientsRef(mealIngredientsRefs)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completableObserver);
    }

    public void insertIngredients(List<Ingredient> ingredients, CompletableObserver completableObserver){

        mealDAO.insertIngredients(ingredients)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completableObserver);
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
