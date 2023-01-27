package com.soha.foodplanner.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.soha.foodplanner.R;
import com.soha.foodplanner.data.data_source.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.repository.Repository;
import com.soha.foodplanner.ui.addapters.CategoryAdapter;
import com.soha.foodplanner.ui.common.AddToFavourite;
import com.soha.foodplanner.ui.home.presenter.HomePresenterListener;
import com.soha.foodplanner.ui.home.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomePresenterListener , AddToFavourite {
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    List<String> categoryItemList = new ArrayList<String>();
    List<CategoryWithMeals> mealsListItem = new ArrayList<>();
    HomePresenter homePresenter;
    Repository repo;


    @SuppressLint("CheckResult")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = new Repository(requireContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);



        homePresenter =new HomePresenter(this,repo);

        recyclerView = view.findViewById(R.id.recycler);
        categoryAdapter = new CategoryAdapter(mealsListItem,this);
        recyclerView.setAdapter(categoryAdapter);

        getCategories();
        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void getCategories() {
        homePresenter.getCategories();
        getCategoryMeals(categoryItemList);

    }

    @Override
    public void getCategoryMeals(List<String> categoryItemList) {
        homePresenter.getMealsOfCategory(categoryItemList);
    }

    @Override
    public void addMealToAdapter(List<MinMeal> minMeals,String s) {
        categoryAdapter.addNewCategory(minMeals, s);

    }

    @Override
    public void addFavouriteMeal(MinMeal minMeal){
        homePresenter.insertToFav(minMeal);

    }
}