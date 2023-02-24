package com.soha.foodplanner.ui.meal_details;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.model.CompleteIngredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private List<CompleteIngredient> ingredients;
    private RequestBuilder<Bitmap> glideRequestBuilder;

    public IngredientsAdapter(List<CompleteIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public IngredientsAdapter() {
        this.ingredients = new ArrayList<>();
    }

    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_complete_ingredient_item,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder holder, int position) {
        CompleteIngredient ingredient = ingredients.get(position);
        if (glideRequestBuilder == null)
            glideRequestBuilder = Glide.with(holder.itemView)
                    .asBitmap()
                    .load(R.drawable.unknown_meal)
                    .override(holder.imageViewThumbnail.getWidth())
                    .placeholder(R.drawable.unknown_meal)
                    .error(R.drawable.unknown_meal);

        glideRequestBuilder.load(ingredient.getThumbnailUrl())
        .into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                ColorStateList backgroundTint =ColorStateList.valueOf(resource
                        .getPixel(resource.getHeight()/2,resource.getWidth()/2))
                        .withAlpha(150);
                holder.imageViewThumbnail.setBackgroundTintList(backgroundTint);
                holder.imageViewThumbnail.setImageBitmap(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
        holder.textViewName.setText(ingredient.getName());
        holder.textViewMeasure.setText(ingredient.getMeasure());
    }

    public void setIngredients(List<CompleteIngredient> ingredients) {
        this.ingredients = ingredients;
        notifyItemRangeInserted(0,ingredients.size());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewMeasure;
        private final ImageView imageViewThumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewMeasure = itemView.findViewById(R.id.textViewMeasure);
        }
    }
}
