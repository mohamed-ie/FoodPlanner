package com.soha.foodplanner.ui.main.planned;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.entities.PlannedMeals;


import java.util.List;


public class PlannedAdapter extends RecyclerView.Adapter<PlannedAdapter.ViewHolder> {
    private List<String> weekDays;
    List<PlannedMeals> dayOfWeekMeals;

    private static final String Tag="Recycler";


    public PlannedAdapter(List<String> myList
    ,List<PlannedMeals> dayWeekMeals
                          ){
        weekDays=myList;
        dayOfWeekMeals=dayWeekMeals;


    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dayName;
        private RecyclerView recyclerViewPlannedItem;

        public ViewHolder(View v) {
            super(v);
            dayName = v.findViewById(R.id.day_name);
            v.findViewById(R.id.recycler_planned_item);


        }
    }

    @NonNull
    @Override
    public PlannedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycler_view_planned_meal_item,parent,false);
        return new PlannedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dayName.setText(weekDays.get(position));
       // holder.recyclerViewPlannedItem.setAdapter(new MealAdapter(PlannedItemAdapter(List<>)));

    }

    @Override
    public int getItemCount() {
        return weekDays.size();
    }




    }



