package com.soha.foodplanner.ui.filter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.model.MinMeal;

import java.util.List;

public class MealsFilterAdapter extends RecyclerView.Adapter<MealsFilterAdapter.ViewHolder> {
    private List<MinMeal> minMeals;
    private final OnMealItemClickListener listener;

    public MealsFilterAdapter(List<MinMeal> minMeals, OnMealItemClickListener listener) {
        this.minMeals = minMeals;
        this.listener = listener;
    }

    public List<MinMeal> getMinMeals() {
        return minMeals;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMinMeals(List<MinMeal> minMeals) {
        this.minMeals = minMeals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealsFilterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycler_view_filter_meal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MealsFilterAdapter.ViewHolder holder, int position) {
        MinMeal minMeal = minMeals.get(position);

        Glide.with(holder.textViewName)
                .load(minMeal.getThumbnailUrl())
                .into(holder.imageViewThumbnail);

        holder.textViewName.setText(minMeal.getName());

        holder.imageButtonFavourite.setOnClickListener(v -> listener.onMealItemClick(minMeal.getId()));
        holder.constraintLayout.setOnClickListener(v -> listener.onMealItemClick(minMeal.getId()));
        holder.imageButtonFavourite.setOnClickListener(v -> listener.onFavouriteClick(minMeal.getId()));
    }

    @Override
    public int getItemCount() {
        return minMeals.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final ImageButton imageButtonFavourite;
        private final ImageView imageViewThumbnail;
        private final ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.editTextName);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
            imageButtonFavourite = itemView.findViewById(R.id.imageButtonFavourite);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
