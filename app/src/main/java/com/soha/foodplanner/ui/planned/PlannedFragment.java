package com.soha.foodplanner.ui.planned;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.AppDatabase;
import com.soha.foodplanner.data.local.entities.PlannedMeals;
import com.soha.foodplanner.data.repository.MealsLocalDataSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class PlannedFragment extends Fragment {


    MealsLocalDataSource repo;
    RecyclerView recyclerView;
    List<String> data;
    PlannedAdapter plannedAdapter;
    WeekSliderAdapter weekSliderAdapter;
    ViewPager2 viewPager2;
    ImageButton next,prev;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        //recyclerView = view.findViewById(R.id.planned_fragment_recycler);
        viewPager2=view.findViewById(R.id.view_pager_planned);
        next=view.findViewById(R.id.next_week_button);
        prev=view.findViewById(R.id.prev_week_button);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager2.getCurrentItem()!=3){
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);

                }

            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View v) {
                if(viewPager2.getCurrentItem()!=0)
                viewPager2.setCurrentItem(viewPager2.getCurrentItem()-1);
            }
        });

        data = Arrays.asList("Saturday"
                , "Sunday"
                , "Monday"
                , "Tuesday"
                , "Wednesday"
                , "Thursday"
                , "Friday");


        repo = new MealsLocalDataSource(AppDatabase.getInstance(requireContext()));
        repo.getPlanedMeal().subscribeOn(Schedulers.io())
                .map(plannedMealsWithMeals -> plannedMealsWithMeals.stream().map(e -> e.getPlannedMeals()).collect(Collectors.toList()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PlannedMeals>>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void accept(List<PlannedMeals> meals) throws Throwable {
                        //plannedAdapter = new PlannedAdapter(data, meals);
                        //recyclerView.setAdapter(plannedAdapter);

                        weekSliderAdapter=new WeekSliderAdapter(data,meals);
                        viewPager2.setAdapter(weekSliderAdapter);


                    }
                });
    }
}