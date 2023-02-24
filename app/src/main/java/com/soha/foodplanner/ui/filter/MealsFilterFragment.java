package com.soha.foodplanner.ui.filter;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.soha.foodplanner.MyApp;
import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.ui.common.TextWatcherAdapter;
import com.soha.foodplanner.ui.common.fragment.BaseFragmentWithArgs;
import com.soha.foodplanner.ui.filter.presenter.MealsFilterFactory;
import com.soha.foodplanner.ui.filter.presenter.MealsFilterPresenter;
import com.soha.foodplanner.ui.filter.presenter.MealsFilterPresenterListener;

import java.util.ArrayList;
import java.util.List;

public class MealsFilterFragment extends BaseFragmentWithArgs<MealsFilterPresenter, com.soha.foodplanner.ui.filter.MealsFilterFragmentArgs>
        implements
        OnMealItemClickListener,
        MealsFilterPresenterListener {
    private MealsFilterAdapter mealsFilterAdapter;
    private TextInputLayout textInputLayoutSearch;
    private TextInputEditText textInputEditTextSearch;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_meals_filter;
    }

    @Override
    protected Factory<MealsFilterPresenter> getPresenterFactory() {
        return new MealsFilterFactory(((MyApp) requireActivity().getApplication()).getMealsRepository());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setListener(this);
        if (args.getCategory() != null)
            presenter.loadByCategory(args.getCategory());
        else if (args.getArea() != null)
            presenter.loadByArea(args.getArea());
        else if (args.getIngredient() != null) {
            presenter.loadByIngredient(args.getIngredient());
        }
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
    }

    @Override
    protected void initViews(View view) {
        textInputLayoutSearch = view.findViewById(R.id.textInputLayoutSearch);
        textInputEditTextSearch = view.findViewById(R.id.textInputEditTextSearch);
        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerViewMeals = view.findViewById(R.id.recyclerViewMeals);
        mealsFilterAdapter = new MealsFilterAdapter(new ArrayList<>(), this);
        recyclerViewMeals.setAdapter(mealsFilterAdapter);
    }

    @Override
    protected void setListeners() {
        textInputLayoutSearch.setStartIconOnClickListener(v -> navController.popBackStack());
        textInputEditTextSearch.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.searchByName(s);
            }
        });
    }

    @Override
    public void onFavouriteClick(long name) {
        presenter.addToFavourite(name);
    }

    @Override
    public void onMealItemClick(long id) {
//        navController.navigate(MealsFilterFragmentDirections.actionMealsFilterFragmentToMealDetails(id, true));
    }

    @Override
    public com.soha.foodplanner.ui.filter.MealsFilterFragmentArgs getSafeArgs() {
        return com.soha.foodplanner.ui.filter.MealsFilterFragmentArgs.fromBundle(requireArguments());
    }


    @Override
    public void onGetMealsByCategorySuccess(List<MinMeal> minMeals) {
        mealsFilterAdapter.setMinMeals(minMeals);
    }

    @Override
    public void onGetMealsByCategoryError(String message) {

    }

    @Override
    public void onGetMealsByCategoryLoading() {

    }
}