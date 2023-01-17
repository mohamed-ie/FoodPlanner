package com.soha.foodplanner.data.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.soha.foodplanner.data.local.AppDatabase;
import com.soha.foodplanner.data.local.FavMealDAO;
import com.soha.foodplanner.data.local.MealsItem;
import java.util.List;

public class Repository {
    private Context context;
    private FavMealDAO favMealDAO;
    private LiveData<List<MealsItem>> storedMeals;

    public Repository(Context context){
        this.context=context;
        AppDatabase db= AppDatabase.getInstance(context.getApplicationContext());
        favMealDAO=db.favMealDAO();
        storedMeals= favMealDAO.getAllMeals();

    }

    public LiveData<List<MealsItem>> getStoredProducts(){
        return storedMeals;

    }
    public void delete(MealsItem meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                favMealDAO.deleteMeal(meal);
            }
        }).start();
    }

    public void insert(MealsItem meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                favMealDAO.insertMeal(meal);
            }
        }).start();
    }
}
