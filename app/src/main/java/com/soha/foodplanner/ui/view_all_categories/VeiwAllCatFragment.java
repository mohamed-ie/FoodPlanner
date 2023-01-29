package com.soha.foodplanner.ui.view_all_categories;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soha.foodplanner.MyApp;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.data_source.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.data_source.remote.webservice.Webservice;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.repository.MealsLocalDataSource;
import com.soha.foodplanner.ui.MainActivity;
import com.soha.foodplanner.ui.common.AddToFavourite;
import com.soha.foodplanner.ui.view_all_categories.presenter.VeiwAllCatPresenter;
import com.soha.foodplanner.ui.view_all_categories.presenter.ViewAllCatListener;

import java.util.List;


public class VeiwAllCatFragment extends Fragment implements ViewAllCatListener, AddToFavourite {
    String categoryName;
    VeiwAllCatPresenter veiwAllCatPresenter;
    private TextView category;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_veiw_all_cat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryName = VeiwAllCatFragmentArgs.fromBundle(requireArguments()).getCatergoryName();
        category = view.findViewById(R.id.category_meal);
        recyclerView = view.findViewById(R.id.recycler_category_cat);
        category.setText(categoryName);

        veiwAllCatPresenter = new VeiwAllCatPresenter(this, ((MyApp) ((MainActivity) requireHost()).getApplication()).getMealsRepository());
        veiwAllCatPresenter.getMealsOfCategory(categoryName);

    }

    @Override
    public void addCategoryMealsToAdapter(List<MinMeal> minMeal) {
        recyclerView.setAdapter(new ViewAllAdapter(minMeal, this));
    }

    @Override
    public void addFavouriteMeal(long id) {
        veiwAllCatPresenter.insertToFav(id);

    }
}