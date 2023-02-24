package com.soha.foodplanner.ui.multi_filter.filter_dialog.adapter.ingredient;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.ui.multi_filter.filter_dialog.OnIngredientFilterItemClickListener;

import java.util.List;

public class FilterIngredientAdapter extends RecyclerView.Adapter<FilterIngredientAdapter.ViewHolder> {
    private List<MinIngredient> minIngredients;
    private final OnIngredientFilterItemClickListener listener;
    private final RequestManager requestManager;

    public FilterIngredientAdapter(List<MinIngredient> minIngredients, RequestManager requestManager, OnIngredientFilterItemClickListener listener) {
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_view_multi_filter_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MinIngredient ingredient = minIngredients.get(position);
        holder.imageViewThumbnail.layout(0,0,0,0);
        requestManager
                .load(ingredient.getThumbnailUrl())
                .override(80,80)
                .placeholder(R.drawable.unknown_meal)
                .error(R.drawable.unknown_meal)
                .circleCrop()
                .into(holder.imageViewThumbnail);

        holder.textViewName.setText(ingredient.getName());
        holder.imageViewThumbnail.setOnClickListener(v -> listener.onIngredientClick(ingredient.getName(),true));
        holder.textViewName.setOnClickListener(v -> listener.onIngredientClick(ingredient.getName(),true));
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
            textViewName = itemView.findViewById(R.id.textViewMealName);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
        }
    }
}
