package com.soha.foodplanner.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.data.mapper.MealMapper;
import com.soha.foodplanner.data.model.CompleteMeal;
import com.soha.foodplanner.data.remote.home.HomeRemoteDataSourceImpl;
import com.soha.foodplanner.data.remote.home.HomeRemoteDataSourceListener;
import com.soha.foodplanner.data.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.remote.webservice.Webservice;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements HomeRemoteDataSourceListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new HomeRemoteDataSourceImpl(Webservice.getInstance().build(Constants.BASE_URL_THE_MEAL_DB, TheMealDBWebService.class), new MealMapper(), this).getRandomMeals(10);
    }

    @Override
    public void onFailed(String message) {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onNextRandomMeal(CompleteMeal completeMeal) {
        Log.i("TAG", "onNextRandomMeal: ");
    }
}