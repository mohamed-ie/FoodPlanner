package com.soha.foodplanner.ui.multi_filter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.soha.foodplanner.MyApp;
import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.ui.MainActivity;
import com.soha.foodplanner.ui.common.fragment.BaseFragment;
import com.soha.foodplanner.ui.common.TextWatcherAdapter;
import com.soha.foodplanner.ui.filter.OnMealItemClickListener;
import com.soha.foodplanner.ui.multi_filter.presenter.MultiFilterFactory;
import com.soha.foodplanner.ui.multi_filter.presenter.MultiFilterPresenter;
import com.soha.foodplanner.ui.multi_filter.presenter.MultiFilterPresenterListener;

import java.util.ArrayList;


public class MultiFilterFragment extends BaseFragment<MultiFilterPresenter>
        implements MultiFilterPresenterListener,
        OnMealItemClickListener {

    private ProgressBar progressBar;
    private TextView textViewPercent;
    private ImageButton imageButtonFilter;
    private CompleteMealAdapter adapterCompleteMeal;
    private TextInputEditText textInputEditTextSearch;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_multi_filter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getAllMeals();
    }

    @Override
    protected Factory<MultiFilterPresenter> getPresenterFactory() {
        return new MultiFilterFactory(((MyApp) ((MainActivity) requireHost()).getApplication()).getMealsRepository(), this);
    }


    @Override
    protected void initViews(View view) {
        super.initViews(view);
        progressBar = view.findViewById(R.id.progressBar);
        textViewPercent = view.findViewById(R.id.textViewPercent);
        imageButtonFilter = view.findViewById(R.id.imageButtonFilter);
        textInputEditTextSearch=view.findViewById(R.id.textInputEditTextSearch);
        initMealsRecyclerView(view);
    }

    private void initMealsRecyclerView(View view) {
        adapterCompleteMeal = new CompleteMealAdapter(new ArrayList<>(), this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewMeals);
        recyclerView.setAdapter(adapterCompleteMeal);
    }

    @Override
    protected void setListeners() {
        imageButtonFilter.setOnClickListener(v -> navController.navigate(MultiFilterFragmentDirections.actionMultiFilterFragmentToFilterDialogFragment(null)));
        textInputEditTextSearch.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                presenter.searchByName(s);
            }
        });
    }

    @Override
    public void onFavouriteClick(long id) {

    }

    @Override
    public void onMealItemClick(long id) {

    }

    @Override
    protected void updateUi() {
        textViewPercent.setText(getString(R.string.percent, 0));

    }

    @Override
    public void onNextMeal(CompleteMeal completeMeal, int progress) {
        adapterCompleteMeal.addMeal(completeMeal.getMeal());
        updateProgress(progress);
    }

    private void updateProgress(int progress) {
        if (progressBar.getProgress() != progress) {
            progressBar.setProgress(progress, true);
            textViewPercent.setText(getString(R.string.percent, progress));
        }
    }

    @Override
    public void onLoadAllMealsComplete() {
        progressBar.setVisibility(View.GONE);
        textViewPercent.setVisibility(View.GONE);
    }

    @Override
    public void onLoadAllMealsError(String message, int progress) {
        progressBar.setProgress(progress);
    }
}