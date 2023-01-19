package com.soha.foodplanner.ui.planned;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.Meal;
import com.soha.foodplanner.ui.addapters.OnItemClickListener;

import java.util.List;


public class PlannedAdapter extends RecyclerView.Adapter<PlannedAdapter.ViewHolder> {
    private List<String> weekDays;
    private static final String Tag="Recycler";


    public PlannedAdapter(List<String> myList){
        weekDays=myList;


    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dayName;

        public ViewHolder(View v) {
            super(v);
            dayName = v.findViewById(R.id.day_name);


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

    }

    @Override
    public int getItemCount() {
        return weekDays.size();
    }




    }



