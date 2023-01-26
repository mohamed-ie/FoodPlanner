package com.soha.foodplanner.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.soha.foodplanner.MyApp;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.data_source.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.data_source.remote.webservice.Webservice;
import com.soha.foodplanner.data.dto.category.CategoryDto;
import com.soha.foodplanner.data.dto.min_meal.MinMealDto;
import com.soha.foodplanner.data.local.CachedMealsWithMeal;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.mapper.MealMapper;
import com.soha.foodplanner.data.mapper.MealMapperImpl;
import com.soha.foodplanner.ui.MainActivity;
import com.soha.foodplanner.ui.addapters.CategoryAdapter;
import com.soha.foodplanner.ui.search.adapter.category.OnCategoryItemClickListener;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomeFragment extends Fragment implements OnCategoryItemClickListener {
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    List<String> categoryItemList = new ArrayList<String>();
    List<CategoryWithMeals> mealsListItem = new ArrayList<>();
    TheMealDBWebService theMealDBWebService;


    @SuppressLint("CheckResult")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = view.findViewById(R.id.recycler);
        categoryAdapter = new CategoryAdapter(mealsListItem);
        recyclerView.setAdapter(categoryAdapter);
        theMealDBWebService = Webservice.getInstance().getTheMealDBWebService();
        Single<CategoryDto> single = theMealDBWebService.getAllCategories();
        MealMapper mapper = new MealMapperImpl();
        single.subscribeOn(Schedulers.io())
                .map(mapper::map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<String>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<String> strings) {
                        categoryItemList = strings;
                        getMeals(strings);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });


        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    void getMeals(List<String> categoryItemList) {

        MealMapper mapper = new MealMapperImpl();
        Disposable disposable = Flowable.fromIterable(categoryItemList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> theMealDBWebService.getMealsByCategory(s)
                        .subscribeOn(Schedulers.io())
                        .map(mapper::map)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<List<MinMeal>>() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<MinMeal> minMeals) {
                                categoryAdapter.addNewCategory(minMeals, s);
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                            }
                        }));
    }

    @Override
    public void onCategoryClick(String category) {

    }
}