package com.soha.foodplanner.ui.day_of_week;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.soha.foodplanner.R;
import com.soha.foodplanner.ui.signup.LoadingFragment;


public class DayOfWeekFragment extends DialogFragment {


    private ListView listView;
    private int dayNum;
    private String dayName;
    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController= NavHostFragment.findNavController(DayOfWeekFragment.this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_of_week, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        listView=view.findViewById(R.id.week_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dayNum =position;
                Log.e("TAG", "onItemClick: "+position );
                setDayName(position);
                Toast.makeText(getContext(),dayName,Toast.LENGTH_LONG).show();
                navController.popBackStack();
            }
        });

    }
    private void setDayName(int dayNumber){

        switch (dayNumber){

            case 0:
                dayName="Saturday";
            break;
            case 1:
                dayName="Sunday";
            break;
            case 2:
                dayName="Monday";
            break;
            case 3:
                dayName="Tuesday";
            break;
            case 4:
                dayName="Wednesday";
            break;
            case 5:
                dayName="Thursday";
            break;
            case 6:
                dayName="Friday";
            break;
        }

    }

}