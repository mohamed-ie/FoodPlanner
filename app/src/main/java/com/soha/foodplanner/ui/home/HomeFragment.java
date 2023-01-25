package com.soha.foodplanner.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soha.foodplanner.R;
import com.soha.foodplanner.data.mapper.CategoryMapper;
import com.soha.foodplanner.data.remote.dto.category.CategoryDto;
import com.soha.foodplanner.data.remote.dto.min_meal.MinMealDto;
import com.soha.foodplanner.data.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.remote.webservice.Webservice;
import com.soha.foodplanner.ui.addapters.CategoryAdapter;
import com.soha.foodplanner.ui.search.OnCategoryItemClickListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomeFragment extends Fragment implements OnCategoryItemClickListener {
    RecyclerView recyclerView;

    List<String> categoryItemList = new ArrayList<String>();
    List<MinMealDto> mealsListItem = new ArrayList<>();
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
        View view=inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = view.findViewById(R.id.recycler);
        theMealDBWebService = Webservice.getInstance().getTheMealDBWebService();
        Single<CategoryDto> single = theMealDBWebService.getAllCategories();
        CategoryMapper mapper = new CategoryMapper();
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

        Observable.fromIterable(categoryItemList)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<MinMealDto>>() {
                    @Override
                    public ObservableSource<MinMealDto> apply(String s) throws Throwable {
                        return (ObservableSource<MinMealDto>) theMealDBWebService.getMealsByCategory(s).toObservable();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MinMealDto>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull MinMealDto minMealDto) {
                        mealsListItem.add(minMealDto);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e("TAG", "onError: " );
                    }

                    @Override
                    public void onComplete() {
                        recyclerView.setAdapter(new CategoryAdapter("Random", mealsListItem));

                    }
                });

    }

    @Override
    public void onCategoryClick(String category) {

    }
}