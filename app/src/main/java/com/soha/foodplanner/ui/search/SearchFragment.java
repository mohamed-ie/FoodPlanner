package com.soha.foodplanner.ui.search;

import android.view.View;
import android.widget.SearchView;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.utils.widget.MotionLabel;
import androidx.recyclerview.widget.RecyclerView;

import com.soha.foodplanner.MyApp;
import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.ui.common.BaseFragment;
import com.soha.foodplanner.ui.main.MainFragment;
import com.soha.foodplanner.ui.search.adapter.area.AreaAdapter;
import com.soha.foodplanner.ui.search.adapter.area.OnAreaItemClickListener;
import com.soha.foodplanner.ui.search.adapter.category.CategoryAdapter;
import com.soha.foodplanner.ui.search.adapter.category.OnCategoryItemClickListener;
import com.soha.foodplanner.ui.search.adapter.ingredient.IngredientAdapter;
import com.soha.foodplanner.ui.search.adapter.ingredient.OnIngredientItemClickListener;
import com.soha.foodplanner.ui.search.adapter.search_by_name.SearchByNameAdapter;
import com.soha.foodplanner.ui.search.presenter.SearchPresenter;
import com.soha.foodplanner.ui.search.presenter.SearchPresenterFactory;
import com.soha.foodplanner.ui.search.presenter.SearchPresenterListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends BaseFragment<SearchPresenter> implements
        SearchPresenterListener,
        OnIngredientItemClickListener,
        OnCategoryItemClickListener,
        OnAreaItemClickListener {
    private SearchView searchView;
    private MotionLabel buttonCancel;
    private MotionLayout motionLayout;
    private SearchByNameAdapter adapterSearchByName;
    private IngredientAdapter adapterIngredient;
    private CategoryAdapter adapterCategory;
    private AreaAdapter adapterArea;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    protected Factory<SearchPresenter> getPresenterFactory() {
        return new SearchPresenterFactory(((MyApp) requireActivity().getApplication()).getMealsRepository(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadAreas();
        presenter.loadIngredients();
        presenter.loadCategories();
    }

    @Override
    protected void initViews(View view) {
        searchView = view.findViewById(R.id.searchView);
        motionLayout = view.findViewById(R.id.motionLayout);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        initSearchByNameRecyclerView(view);
        initAreasRecyclerView(view);
        initIngredientsRecyclerView(view);
        initCategoriesRecyclerView(view);
    }

    private void initCategoriesRecyclerView(View view) {
        adapterCategory = new CategoryAdapter(new ArrayList<>(), this);
        RecyclerView recyclerViewCategories = view.findViewById(R.id.recyclerViewCategories);
//        recyclerViewCategories.setLayoutManager(new GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false));
        recyclerViewCategories.setAdapter(adapterCategory);

    }

    private void initIngredientsRecyclerView(View view) {
        adapterIngredient = new IngredientAdapter(new ArrayList<>(), this);
        RecyclerView recyclerViewIngredients = view.findViewById(R.id.recyclerViewIngredient);
        recyclerViewIngredients.setAdapter(adapterIngredient);
    }

    private void initAreasRecyclerView(View view) {
        adapterArea = new AreaAdapter(new ArrayList<>(), this);
        RecyclerView recyclerViewAreas = view.findViewById(R.id.recyclerViewAreas);
        recyclerViewAreas.setAdapter(adapterArea);
    }

    private void initSearchByNameRecyclerView(View view) {
        adapterSearchByName = new SearchByNameAdapter(new ArrayList<>());
        RecyclerView recyclerViewSearchByNameResult = view.findViewById(R.id.recyclerViewSearchByNameResult);
        recyclerViewSearchByNameResult.setAdapter(adapterSearchByName);
    }

    @Override
    protected void setListeners() {
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                motionLayout.transitionToEnd();
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.searchByName(newText, adapterSearchByName.getNames());
                return true;
            }
        });

        buttonCancel.setOnClickListener(v -> {
            motionLayout.transitionToStart();
            searchView.clearFocus();
        });
    }

    @Override
    public void onSearchSuccess(List<String> names) {
        adapterSearchByName.setNames(names);
    }

    @Override
    public void onSearchError(String message) {

    }

    @Override
    public void onSearchLoading() {

    }

    @Override
    public void onGetAllAreasSuccess(List<String> areas) {
        adapterArea.setAreas(areas);
    }

    @Override
    public void onGetAllAreasLoading() {

    }

    @Override
    public void onGetAllAreasError(String message) {

    }

    @Override
    public void onGetAllIngredientSuccess(List<MinIngredient> ingredients) {
        adapterIngredient.setMinIngredients(ingredients);
    }

    @Override
    public void onGetAllIngredientError(String message) {

    }

    @Override
    public void onGetAllIngredientLoading() {

    }

    @Override
    public void onGetAllCategoriesSuccess(List<String> categories) {
        adapterCategory.setCategories(categories);
    }

    @Override
    public void onGetAllCategoriesError(String message) {

    }

    @Override
    public void onGetAllCategoriesLoading() {

    }


    @Override
    public void onAreaItemClick(String area) {
        navController.navigate(SearchFragmentDirections.actionSearchFragmentToMealsFilterFragment(null, area));

    }

    @Override
    public void onIngredientClick(String name) {

    }

    @Override
    public void onCategoryClick(String category) {
        navController.navigate(SearchFragmentDirections.actionSearchFragmentToMealsFilterFragment(category, null));
    }
}