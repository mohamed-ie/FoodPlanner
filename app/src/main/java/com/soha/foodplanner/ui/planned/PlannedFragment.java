package com.soha.foodplanner.ui.planned;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.PlannedMeals;
import com.soha.foodplanner.data.repository.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class PlannedFragment extends Fragment {


    Repository repo;
    RecyclerView recyclerView;
    List<String> data;
    PlannedAdapter plannedAdapter;

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
        recyclerView=view.findViewById(R.id.planned_fragment_recycler);

        data= Arrays.asList("Saturday"
                ,"Sunday"
                ,"Monday"
                ,"Tuesday"
                ,"Wednesday"
                ,"Thursday"
                ,"Friday");


        repo=new Repository(requireContext());
        repo.getPlanedMeal().subscribeOn(Schedulers.io())
                .map(plannedMealsWithMeals -> plannedMealsWithMeals.stream().map(e->e.getPlannedMeals()).collect(Collectors.toList()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PlannedMeals>>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void accept(List<PlannedMeals> meals) throws Throwable {
                        plannedAdapter=new PlannedAdapter(data,meals);
                        recyclerView.setAdapter(plannedAdapter);
                    }
                });
    }
}