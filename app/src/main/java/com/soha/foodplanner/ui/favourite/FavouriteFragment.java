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
import com.soha.foodplanner.data.data_source.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.data_source.remote.webservice.Webservice;
import com.soha.foodplanner.data.local.FavouriteMealsWithMeal;

import com.soha.foodplanner.data.repository.Repository;
import com.soha.foodplanner.ui.favourite.presenter.FavouritePresenter;
import com.soha.foodplanner.ui.favourite.presenter.FavouritePresenterListener;

import java.util.List;


public class FavouriteFragment extends Fragment implements FavouritePresenterListener {
    private Repository repo;
    private FavAdapter favAdapter;
    private RecyclerView recyclerView;
    private FavouritePresenter favouritePresenter;
    private TheMealDBWebService theMealDBWebService;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView=view.findViewById(R.id.recycler_category_fav);

        repo=new Repository(requireContext());
        theMealDBWebService= Webservice.getInstance().getTheMealDBWebService();
        favouritePresenter =new FavouritePresenter(this,theMealDBWebService,repo);

        getAllFavouriteMeals();


    }

    @Override
    public void getAllFavouriteMeals() {
        favouritePresenter.getFavourites(repo);
    }

    @Override
    public void addFavMealToAdapter(List<FavouriteMealsWithMeal> meals) {
        favAdapter=new FavAdapter(meals, this);
        favAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(favAdapter);
    }

    @Override
    public void deleteMealFromFav(FavouriteMealsWithMeal favouriteMealsWithMeal) {
        favouritePresenter.deleteFromFavourite(favouriteMealsWithMeal);
    }
}