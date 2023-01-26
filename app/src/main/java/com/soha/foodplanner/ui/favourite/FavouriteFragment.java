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

import com.soha.foodplanner.data.repository.Repository;
import com.soha.foodplanner.ui.favourite.presenter.FavouritePresenter;
import com.soha.foodplanner.ui.favourite.presenter.FavouritePresenterListener;
import com.soha.foodplanner.ui.home.presenter.HomePresenter;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FavouriteFragment extends Fragment implements FavouritePresenterListener {
    Repository repo;
    FavAdapter favAdapter;
    RecyclerView recyclerView;
    FavouritePresenter favouritePresenter;


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

        favouritePresenter =new FavouritePresenter(this);
        recyclerView=view.findViewById(R.id.recycler_category_fav);

        repo=new Repository(requireContext());
        getAllFavouriteMeals();

//        repo.getFavMeal().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe((Consumer<? super List<FavouriteMealsWithMeal>>) new Consumer<List<FavouriteMealsWithMeal>>() {
//                    @SuppressLint("CheckResult")
//                    @Override
//                    public void accept(List<FavouriteMealsWithMeal> meals) throws Throwable {
//                       favAdapter=new FavAdapter(meals);
//                       favAdapter.notifyDataSetChanged();
//                       recyclerView.setAdapter(favAdapter);
//                    }
//                });


    }

    @Override
    public void getAllFavouriteMeals() {
        favouritePresenter.getFavourites(repo);
    }

    @Override
    public void addFavMealToAdapter(List<FavouriteMealsWithMeal> meals) {
        favAdapter=new FavAdapter(meals);
        favAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(favAdapter);
    }
}