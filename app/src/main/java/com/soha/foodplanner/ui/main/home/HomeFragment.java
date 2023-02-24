package com.soha.foodplanner.ui.main.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.soha.foodplanner.MyApp;
import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.ui.MainActivity;
import com.soha.foodplanner.ui.addapters.VerticalStackTransformer;
import com.soha.foodplanner.ui.common.OnInspirationItemListener;
import com.soha.foodplanner.ui.common.fragment.BaseFragment;
import com.soha.foodplanner.ui.main.home.presenter.HomePresenter;
import com.soha.foodplanner.ui.main.home.presenter.HomePresenterFactory;
import com.soha.foodplanner.ui.main.home.presenter.HomePresenterListener;

import java.util.ArrayList;


public class HomeFragment extends BaseFragment<HomePresenter<HomePresenterListener>> implements HomePresenterListener, OnInspirationItemListener {
    private InspirationAdapter adapterInspirations;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected Factory<HomePresenter<HomePresenterListener>> getPresenterFactory() {
        return new HomePresenterFactory(((MyApp) ((MainActivity) requireHost()).getApplication()).getMealsRepository());
    }

    @Override
    protected void initViews(View view) {
        initInspirationsRecyclerView(view);
    }

    @Override
    protected void updateUi() {
        presenter.setListener(this);
        presenter.loadInspirations();
    }

    private void initInspirationsRecyclerView(View view) {
        ViewPager2 viewPager2Inspirations = view.findViewById(R.id.viewPagerInspirations);
        adapterInspirations = new InspirationAdapter(new ArrayList<>(), this);
        viewPager2Inspirations.setAdapter(adapterInspirations);
        viewPager2Inspirations.setOffscreenPageLimit(3);
        viewPager2Inspirations.setPageTransformer(new VerticalStackTransformer(3));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void addFavouriteMeal(long id) {

    }

    @Override
    public void viewAll(String name) {

    }

    @Override
    public void openMealDetails(CompleteMeal completeMeal) {
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToMealDetails(true, completeMeal));
    }

    @Override
    public void onNextInspiration(CompleteMeal completeMeal) {
        adapterInspirations.addNewMeal(completeMeal);
    }

}