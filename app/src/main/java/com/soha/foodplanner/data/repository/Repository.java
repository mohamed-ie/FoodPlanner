package com.soha.foodplanner.data.repository;

import android.content.Context;

import com.soha.foodplanner.data.local.AppDatabase;
import com.soha.foodplanner.data.local.Meal;
import com.soha.foodplanner.data.local.MealDAO;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
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

    public void getAllMeals(SingleObserver<List<Meal>> singleObserver){
        mealDAO.getAllMeals().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleObserver);
    }

    public void delete(Meal meal,CompletableObserver completableObserver){
        mealDAO.deleteMeal(meal).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completableObserver);

    }

    public void insert(Meal meal,CompletableObserver completableObserver) {
        mealDAO.insertMeal((meal)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completableObserver);
    }
    public void searchMealByArea(String mealArea,SingleObserver<List<Meal>> mealSingleObserver){

        mealDAO.FindMealByArea(mealArea)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealSingleObserver);
    }
    public void searchMealByCategory(String mealCategory,SingleObserver<List<Meal>> singleObserver){
        mealDAO.FindMealByCategory(mealCategory)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleObserver);
    }
}
