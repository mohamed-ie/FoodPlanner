package com.soha.foodplanner.ui.main.planned;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.entities.PlannedMeals;

import java.util.List;

public class WeekSliderAdapter extends RecyclerView.Adapter<WeekSliderAdapter.WeekSliderViewHolder>{
    private List<String> weekDays;
    private List<PlannedMeals> meals;
    PlannedAdapter plannedAdapter;

    public WeekSliderAdapter(List<String> weekDays, List<PlannedMeals> meals) {
        this.weekDays = weekDays;
        this.meals = meals;
    }

    class WeekSliderViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView recyclerView;


        public WeekSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView=itemView.findViewById(R.id.planned_fragment_recycler);
        }
    }

    @NonNull
    @Override
    public WeekSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeekSliderViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.week_days_item_pager
                ,parent
                ,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull WeekSliderViewHolder holder, int position) {
        plannedAdapter = new PlannedAdapter(weekDays, meals);
        holder.recyclerView.setAdapter(plannedAdapter);
    }

    @Override
    public int getItemCount() {
        return 4;
    }


}
