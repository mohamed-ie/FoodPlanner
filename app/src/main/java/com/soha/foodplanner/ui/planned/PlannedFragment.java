package com.soha.foodplanner.ui.planned;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.soha.foodplanner.MyApp;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.entities.PlanedMealWithMeal;
import com.soha.foodplanner.ui.MainActivity;

import java.util.Arrays;
import java.util.List;


public class PlannedFragment extends Fragment implements PlannedPresenterListener,
        PlannedMealTestListener,
        OnPlannedItemClickListener {
    RecyclerView recyclerView;
    List<String> data;
    PlannedAdapter plannedAdapter;
    WeekSliderAdapter weekSliderAdapter;
    ViewPager2 viewPager2;
    ImageButton next, prev;
    private PlannedPresenter presenter;

    private PlannedAdapterTest adapterTest;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PlannedPresenterImpl(((MyApp) ((MainActivity) requireHost()).getApplication()).getMealsRepository(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_planned, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        adapterTest = new PlannedAdapterTest(this);
        recyclerView.setAdapter(adapterTest);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        presenter.loadPlannedMeals();

        //recyclerView = view.findViewById(R.id.planned_fragment_recycler);
//        viewPager2 = view.findViewById(R.id.view_pager_planned);
//        next = view.findViewById(R.id.next_week_button);
//        prev = view.findViewById(R.id.prev_week_button);
//        presenter.loadPlannedMeals();
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (viewPager2.getCurrentItem() != 3) {
//                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
//
//                }
//
//            }
//        });
//        prev.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("SuspiciousIndentation")
//            @Override
//            public void onClick(View v) {
//                if (viewPager2.getCurrentItem() != 0)
//                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
//            }
//        });
//
//        data = Arrays.asList("Saturday"
//                , "Sunday"
//                , "Monday"
//                , "Tuesday"
//                , "Wednesday"
//                , "Thursday"
//                , "Friday");


//        repo = new MealsLocalDataSource(AppDatabase.getInstance(requireContext()));
//        repo.getPlanedMeal().subscribeOn(Schedulers.io())
//                .map(plannedMealsWithMeals -> plannedMealsWithMeals.stream().map(e -> e.getPlannedMeals()).collect(Collectors.toList()))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<PlannedMeals>>() {
//                    @SuppressLint("CheckResult")
//                    @Override
//                    public void accept(List<PlannedMeals> meals) throws Throwable {
//                        //plannedAdapter = new PlannedAdapter(data, meals);
//                        //recyclerView.setAdapter(plannedAdapter);
//
//                        weekSliderAdapter = new WeekSliderAdapter(data, meals);
//                        viewPager2.setAdapter(weekSliderAdapter);
//
//
//                    }
//                });
    }

    @Override
    public void onNextItem(List<PlanedMealWithMeal> plannedMeals) {
        adapterTest.setPlanedMealWithMeals(plannedMeals);
    }

    @Override
    public void onRemove(long id) {
        presenter.remove(id);
    }
}