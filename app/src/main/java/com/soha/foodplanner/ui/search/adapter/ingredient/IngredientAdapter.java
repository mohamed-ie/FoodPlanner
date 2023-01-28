package com.soha.foodplanner.ui.search.adapter.ingredient;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.model.MinIngredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private List<MinIngredient> minIngredients;
    private final OnIngredientItemClickListener listener;
    private final RequestManager requestManager;

    public IngredientAdapter(List<MinIngredient> minIngredients, RequestManager requestManager, OnIngredientItemClickListener listener) {
        this.minIngredients = minIngredients;
        this.listener = listener;
        this.requestManager = requestManager;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMinIngredients(List<MinIngredient> minIngredients) {
        this.minIngredients = minIngredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_view_ingredient_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
        MinIngredient ingredient = minIngredients.get(position);
        requestManager
                .load(ingredient.getThumbnailUrl())
                .placeholder(R.drawable.unknown_meal)
                .error(R.drawable.unknown_meal)
                .circleCrop()
                .into(holder.imageViewThumbnail);

        holder.textViewName.setText(ingredient.getName());
        holder.imageViewThumbnail.setOnClickListener(v -> listener.onIngredientClick(ingredient.getName()));
        holder.textViewName.setOnClickListener(v -> listener.onIngredientClick(ingredient.getName()));
    }

    @Override
    public int getItemCount() {
        return minIngredients.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final ImageView imageViewThumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
        }
    }
}
