package com.soha.foodplanner.ui.multi_filter.filter_dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.soha.foodplanner.MyApp;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.ui.MainActivity;
import com.soha.foodplanner.ui.common.dialogs.BaseDialogFragmentWithArgs;
import com.soha.foodplanner.ui.common.presenter.factory.PresenterFactory;
import com.soha.foodplanner.ui.common.presenter.store.PresenterStoreImpl;
import com.soha.foodplanner.ui.multi_filter.filter_dialog.presenter.FilterPresenter;
import com.soha.foodplanner.ui.multi_filter.filter_dialog.presenter.FilterPresenterFactory;
import com.soha.foodplanner.ui.multi_filter.filter_dialog.presenter.FilterPresenterListener;

import com.soha.foodplanner.ui.search.adapter.area.AreaAdapter;
import com.soha.foodplanner.ui.search.adapter.area.OnAreaItemClickListener;
import com.soha.foodplanner.ui.search.adapter.category.CategoryAdapter;
import com.soha.foodplanner.ui.search.adapter.category.OnCategoryItemClickListener;
import com.soha.foodplanner.ui.search.adapter.ingredient.IngredientAdapter;
import com.soha.foodplanner.ui.search.adapter.ingredient.OnIngredientItemClickListener;
import com.soha.foodplanner.utils.UiUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterDialogFragment extends BaseDialogFragmentWithArgs<FilterDialogFragmentArgs>
        implements FilterPresenterListener,
        OnIngredientItemClickListener,
        OnCategoryItemClickListener,
        OnAreaItemClickListener {
    public static final String FILTERS = "filters";

    private TextInputEditText textInputEditTextSearch;
    private Button buttonFilter;
    private Button buttonCancel;
    private ImageButton imageButtonExpandCategories;
    private ImageButton imageButtonExpandIngredients;
    private ImageButton imageButtonExpandAreas;
    private CategoryAdapter adapterCategory;
    private IngredientAdapter adapterIngredients;
    private AreaAdapter adapterArea;
    private RecyclerView recyclerViewAreas;
    private RecyclerView recyclerViewCategories;
    private RecyclerView recyclerViewIngredients;

    private int spanCount;
    private FilterPresenter presenter;
    private final PresenterFactory<FilterPresenter> factory = PresenterFactory.getInstance(PresenterStoreImpl.getInstance());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_FoodPlanner_Dialog_Filter);
        initDependencies();
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        spanCount = (int) (width / UiUtils.dpTpPx(80f, getResources()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getAllIngredients();
        presenter.getAllAreas();
        presenter.getAllCategories();
    }

    private void initDependencies() {
        initPresenter();
    }

    private void initPresenter() {
        presenter = factory.create(getLayoutResource(), new FilterPresenterFactory(this, ((MyApp) ((MainActivity) requireHost()).getApplication()).getMealsRepository()));
    }

    @Override
    public int getLayoutResource() {
        return R.layout.dialog_fragment_filter;
    }


    @Override
    public void initViews(@NonNull View view) {
        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonFilter = view.findViewById(R.id.buttonFilter);
        textInputEditTextSearch = view.findViewById(R.id.textInputEditTextSearch);
        imageButtonExpandCategories = view.findViewById(R.id.imageButtonExpandCategories);
        imageButtonExpandAreas = view.findViewById(R.id.imageButtonExpandAreas);
        imageButtonExpandIngredients = view.findViewById(R.id.imageButtonExpandIngredients);
        initAreasRecyclerView(view);
        initCategoriesRecyclerView(view);
        initIngredientsRecyclerView(view);
    }

    private void initCategoriesRecyclerView(View view) {
        adapterCategory = new CategoryAdapter(new ArrayList<>(), this);
        recyclerViewCategories = view.findViewById(R.id.recyclerViewCategories);
        recyclerViewCategories.setLayoutManager(new GridLayoutManager(requireContext(), spanCount, RecyclerView.VERTICAL, false));
        recyclerViewCategories.setAdapter(adapterCategory);
    }

    private void initIngredientsRecyclerView(View view) {
//        adapterIngredients = new IngredientAdapter(new ArrayList<>(), this);
        recyclerViewIngredients = view.findViewById(R.id.recyclerViewIngredients);
        recyclerViewIngredients.setLayoutManager(new GridLayoutManager(requireContext(), spanCount, RecyclerView.VERTICAL, false));
        recyclerViewIngredients.setAdapter(adapterIngredients);
    }

    private void initAreasRecyclerView(View view) {
        adapterArea = new AreaAdapter(new ArrayList<>(), this);
        recyclerViewAreas = view.findViewById(R.id.recyclerViewAreas);
        recyclerViewAreas.setLayoutManager(new GridLayoutManager(requireContext(), spanCount, RecyclerView.VERTICAL, false));
        recyclerViewAreas.setAdapter(adapterArea);
    }

    @Override
    public void setListeners() {
        imageButtonExpandCategories.setOnClickListener(v -> toggleExpand(recyclerViewCategories, imageButtonExpandCategories));
        imageButtonExpandAreas.setOnClickListener(v -> toggleExpand(recyclerViewAreas, imageButtonExpandAreas));
        imageButtonExpandIngredients.setOnClickListener(v -> toggleExpand(recyclerViewIngredients, imageButtonExpandIngredients));

        buttonCancel.setOnClickListener(v -> navController.popBackStack());
        buttonFilter.setOnClickListener(v -> sendDataViaBackStackEntry(Collections.singletonMap(FILTERS, presenter.getState().getFilters())));
    }

    private void toggleExpand(RecyclerView recyclerView, ImageButton imageButton) {
        imageButton.setRotation(imageButton.getRotation() - 180);
        int visibility = View.VISIBLE;
        if (recyclerView.getVisibility() == View.VISIBLE)
            visibility = View.GONE;
        recyclerView.setVisibility(visibility);
    }

    @Override
    public FilterDialogFragmentArgs getSafeArgs() {
        return FilterDialogFragmentArgs.fromBundle(requireArguments());
    }

    @Override
    public void updateUiFromSafeArgs(FilterDialogFragmentArgs args) {
        presenter.setFilters(args.getFilters());
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
    public void onGetAllIngredientSuccess(List<MinIngredient> ingredients) {
        adapterIngredients.setMinIngredients(ingredients);
    }

    @Override
    public void onGetAllIngredientError(String message) {

    }

    @Override
    public void onGetAllIngredientLoading() {

    }

    @Override
    public void onAreaItemClick(String area) {
        presenter.updateArea(area);
    }

    @Override
    public void onCategoryClick(String category) {
        presenter.updateCategory(category);
    }

    @Override
    public void onIngredientClick(String ingredient) {
        presenter.updateIngredient(ingredient);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        factory.onDestroy(getLayoutResource(), requireActivity().isChangingConfigurations());
    }
}