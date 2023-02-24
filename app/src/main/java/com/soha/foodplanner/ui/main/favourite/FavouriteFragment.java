package com.soha.foodplanner.ui.main.favourite;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.soha.foodplanner.MyApp;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.entities.FavouriteMealsWithMeal;

import com.soha.foodplanner.ui.MainActivity;
import com.soha.foodplanner.ui.main.favourite.presenter.FavouritePresenter;
import com.soha.foodplanner.ui.main.favourite.presenter.FavouritePresenterListener;

import java.util.List;


public class FavouriteFragment extends Fragment implements FavouritePresenterListener {
    private FavAdapter favAdapter;
    private RecyclerView recyclerView;
    private FavouritePresenter favouritePresenter;
    private Button restore;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favouritePresenter =new FavouritePresenter(this, ((MyApp) ((MainActivity) requireHost()).getApplication()).getMealsRepository());
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
        restore=view.findViewById(R.id.buttonRestore);
        restore.setOnClickListener(v->{
            favouritePresenter.loadRemoteData();
        });
        getAllFavouriteMeals();
    }

    @Override
    public void getAllFavouriteMeals() {
        favouritePresenter.getFavourites();
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