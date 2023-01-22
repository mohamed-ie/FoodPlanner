package com.soha.foodplanner.ui.filter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.ui.common.BaseFragment;
import com.soha.foodplanner.ui.filter.presenter.MealsFilterFactory;
import com.soha.foodplanner.ui.filter.presenter.MealsFilterPresenter;
import com.soha.foodplanner.ui.filter.presenter.MealsFilterPresenterListener;

import java.util.ArrayList;

public class MealsFilterFragment extends BaseFragment<MealsFilterPresenter> implements
        OnMealItemClickListener,
        MealsFilterPresenterListener {
    private RecyclerView recyclerViewMeals;
    private MealsFilterAdapter mealsFilterAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_meals_filter;
    }

    @Override
    protected Factory<MealsFilterPresenter> getPresenterFactory() {
        return new MealsFilterFactory();
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        recyclerViewMeals = view.findViewById(R.id.recyclerView);
        mealsFilterAdapter = new MealsFilterAdapter(new ArrayList<>(), this);
    }

    @Override
    public void onClick(String data) {

    }

    @Override
    public void onFavouriteClick(String id) {

    }
}