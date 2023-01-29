package com.soha.foodplanner.ui.planned;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.entities.PlanedMealWithMeal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlannedAdapterTest extends RecyclerView.Adapter<PlannedAdapterTest.ViewHolder> {
    List<PlanedMealWithMeal> planedMealWithMeals;
    private final PlannedMealTestListener listener;

    public PlannedAdapterTest(PlannedMealTestListener listener) {
        this.listener = listener;
        planedMealWithMeals=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_planned_test, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlanedMealWithMeal minMeal = planedMealWithMeals.get(position);

        Glide.with(holder.itemView)
                .load(minMeal.getMeals().get(0).getPhotoUri())
                .into(holder.imageViewThumbnail);

        holder.textViewName.setText(minMeal.getMeals().get(0).getName());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ROOT);
        Date dt = new Date(minMeal.getPlannedMeals().getDate());
        String s = sdf.format(dt);
        holder.textViewDate.setText(s);

        holder.imageButtonRemove.setOnClickListener(v -> listener.onRemove(minMeal.getPlannedMeals().getId()));
    }

    public void setPlanedMealWithMeals(List<PlanedMealWithMeal> planedMealWithMeals) {
        this.planedMealWithMeals = planedMealWithMeals;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return planedMealWithMeals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewDate;
        private final ImageButton imageButtonRemove;
        private final ImageView imageViewThumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
            imageButtonRemove = itemView.findViewById(R.id.imageButtonRemove);
        }
    }
}
