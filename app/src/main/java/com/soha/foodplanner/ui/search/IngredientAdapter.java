package com.soha.foodplanner.ui.search;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.model.MinIngredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private List<MinIngredient> minIngredients;
    private final OnIngredientItemClickListener listener;

    public IngredientAdapter(List<MinIngredient> minIngredients, OnIngredientItemClickListener listener) {
        this.minIngredients = minIngredients;
        this.listener = listener;
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
        Glide.with(holder.itemView)
                .load(ingredient.getThumbnailUrl())
                .into(holder.imageViewThumbnail);


        holder.textViewName.setText(ingredient.getName());

        holder.imageViewThumbnail.setOnClickListener(v -> listener.onIngredientClick(ingredient.getName()));
        holder.textViewName.setOnClickListener(v -> listener.onIngredientClick(ingredient.getName()));
    }

    @Override
    public int getItemCount() {
        return minIngredients.size();
    }

    protected static class ViewHolder extends AreaAdapter.ViewHolder {
        private final TextView textViewName;
        private final ImageView imageViewThumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
        }
    }
}
