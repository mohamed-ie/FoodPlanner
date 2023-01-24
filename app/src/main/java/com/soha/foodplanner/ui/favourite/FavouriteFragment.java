package com.soha.foodplanner.ui.favourite;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.FavouriteMealsWithMeal;
import com.soha.foodplanner.data.local.Meal;
import com.soha.foodplanner.data.remote.dto.min_meal.MinMealDto;
import com.soha.foodplanner.data.repository.Repository;
import com.soha.foodplanner.ui.addapters.MealAdapter;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FavouriteFragment extends Fragment {
    Repository repo;
    MealAdapter mealAdapter;
    RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(R.id.recycler_category_fav);

        repo=new Repository(requireContext());
        repo.getFavMeal().subscribeOn(Schedulers.io())
                .map(favouriteMealsWithMeals -> favouriteMealsWithMeals.stream().map(e->e.getMeal()).collect(Collectors.toList()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Meal>>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void accept(List<Meal> meals) throws Throwable {
                        mealAdapter=new MealAdapter(meals);
                        mealAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(mealAdapter);
                    }
                });


    }
}