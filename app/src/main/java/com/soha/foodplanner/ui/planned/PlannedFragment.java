package com.soha.foodplanner.ui.planned;

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

import java.util.Arrays;
import java.util.List;


public class PlannedFragment extends Fragment {

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data= Arrays.asList("Saturday"
        ,"Sunday"
        ,"Monday"
        ,"Tuesday"
        ,"Wednesday"
        ,"Thursday"
        ,"Friday");
        plannedAdapter=new PlannedAdapter(data);
        recyclerView.setAdapter(plannedAdapter);

    }
}