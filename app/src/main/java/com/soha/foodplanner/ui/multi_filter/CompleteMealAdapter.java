package com.soha.foodplanner.ui.multi_filter;

import android.graphics.drawable.Drawable;
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
import com.bumptech.glide.RequestBuilder;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.entities.Meal;
import com.soha.foodplanner.ui.filter.OnMealItemClickListener;

import java.util.List;

public class CompleteMealAdapter extends RecyclerView.Adapter<CompleteMealAdapter.ViewHolder> {
    private List<Meal> meals;
    private final OnMealItemClickListener listener;
    private RequestBuilder<Drawable> glideRequestBuilder;

    public CompleteMealAdapter(List<Meal> meals, OnMealItemClickListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    public List<Meal> getMeals() {
        return meals;
    }


    @NonNull
    @Override
    public CompleteMealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycler_view_filter_meal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        if (glideRequestBuilder == null)
            glideRequestBuilder = Glide.with(holder.itemView)
                    .load(R.drawable.unknown_meal)
                    .override(holder.imageViewThumbnail.getWidth(), holder.imageViewThumbnail.getHeight())
                    .error(R.drawable.unknown_meal)
                    .placeholder(R.drawable.unknown_meal);
        
        glideRequestBuilder.load(meal.getPhotoUri())
                .into(holder.imageViewThumbnail);

        holder.textViewName.setText(meal.getName());

        holder.imageButtonFavourite.setOnClickListener(v -> listener.onMealItemClick(meal.getId()));
        holder.constraintLayout.setOnClickListener(v -> listener.onMealItemClick(meal.getId()));
        holder.imageButtonFavourite.setOnClickListener(v -> listener.onFavouriteClick(meal.getId()));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
        notifyItemInserted(meals.size());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final ImageButton imageButtonFavourite;
        private final ImageView imageViewThumbnail;
        private final ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewMealName);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
            imageButtonFavourite = itemView.findViewById(R.id.imageButtonFavourite);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}