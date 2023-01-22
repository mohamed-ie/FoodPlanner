package com.soha.foodplanner.ui.search;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.utils.widget.MotionLabel;
import androidx.recyclerview.widget.RecyclerView;

import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.mapper.AreaMapper;
import com.soha.foodplanner.data.mapper.CategoryMapper;
import com.soha.foodplanner.data.mapper.Mapper;
import com.soha.foodplanner.data.mapper.MealNameMapper;
import com.soha.foodplanner.data.mapper.MinIngredientMapper;
import com.soha.foodplanner.data.model.MinIngredient;
import com.soha.foodplanner.data.remote.dto.MealDto;
import com.soha.foodplanner.data.remote.dto.area.AreaDto;
import com.soha.foodplanner.data.remote.dto.category.CategoryDto;
import com.soha.foodplanner.data.remote.dto.ingredient.IngredientDto;
import com.soha.foodplanner.data.remote.search.SearchRemoteDataSource;
import com.soha.foodplanner.data.remote.search.SearchRemoteDataSourceImpl;
import com.soha.foodplanner.data.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.remote.webservice.Webservice;
import com.soha.foodplanner.data.repository.search.SearchRepository;
import com.soha.foodplanner.data.repository.search.SearchRepositoryImpl;
import com.soha.foodplanner.ui.common.BaseFragment;
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
        TheMealDBWebService theMealDBWebService = Webservice.getInstance().getTheMealDBWebService();
        Mapper<MealDto, List<String>> mapper = new MealNameMapper();
        Mapper<AreaDto, List<String>> areaMapper = new AreaMapper();
        Mapper<IngredientDto, List<MinIngredient>> ingredientMapper = new MinIngredientMapper();
        Mapper<CategoryDto, List<String>> categoryMapper = new CategoryMapper();
        SearchRemoteDataSource searchPRemoteDataSource = new SearchRemoteDataSourceImpl(
                theMealDBWebService,
                mapper,
                areaMapper,
                categoryMapper, ingredientMapper, this);
        SearchRepository searchRepository = new SearchRepositoryImpl(searchPRemoteDataSource);
        return new SearchPresenterFactory(searchRepository, this);
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
    public void onFailed(String message) {

    }

    @Override
    public void onSearchSuccess(List<String> names) {
        adapterSearchByName.setNames(names);
    }

    @Override
    public void onGetAllAreasSuccess(List<String> areas) {
        adapterArea.setAreas(areas);
  }

    @Override
    public void onGetAllIngredientSuccess(List<MinIngredient> ingredients) {
        adapterIngredient.setMinIngredients(ingredients);
  }

    @Override
    public void onGetAllCategoriesSuccess(List<String> categories) {
        adapterCategory.setCategories(categories);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onAreaItemClick(String area) {

    }

    @Override
    public void onIngredientClick(String name) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
}