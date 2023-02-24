package com.soha.foodplanner.ui.main.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.entities.Meal;
import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.ui.common.OnInspirationItemListener;
import com.soha.foodplanner.utils.card_profile.MealChefBackgroundDrawale;

import java.util.List;

public class InspirationAdapter extends RecyclerView.Adapter<InspirationAdapter.ViewHolder> {
    private final List<CompleteMeal> completeMeals;
    private final OnInspirationItemListener listener;
    private Context context;
    private RequestBuilder<Drawable> glideRequestBuilder;

    public InspirationAdapter(List<CompleteMeal> completeMeals, OnInspirationItemListener onInspirationItemListener) {
        this.completeMeals = completeMeals;
        this.listener = onInspirationItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(
                        R.layout.view_pager_inspiration_item,
                        parent,
                        false
                ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (glideRequestBuilder == null)
            glideRequestBuilder = Glide.with(context)
                    .load(R.drawable.unknown_meal)
                    .override(holder.imageViewThumbnail.getWidth(), holder.imageViewThumbnail.getHeight())
                    .error(R.drawable.unknown_meal)
                    .placeholder(R.drawable.unknown_meal);

        CompleteMeal completeMeal = completeMeals.get(position);
        Meal meal = completeMeal.getMeal();

        holder.textViewName.setText(meal.getName());

        glideRequestBuilder
                .load(meal.getPhotoUri())
                .into(holder.imageViewThumbnail);

        holder.imageViewUser.getViewTreeObserver().addOnPreDrawListener(() -> {
            holder.imageViewUser.setBackground(new MealChefBackgroundDrawale(holder.imageViewUser.getWidth(),
                    holder.imageViewUser.getHeight(),
                    holder.imageViewUser.getPaddingBottom(),
                    holder.imageViewUser.getBackgroundTintList().getDefaultColor()));
            return true;
        });

        holder.imageButtonBookmark.setOnClickListener(v -> listener.addFavouriteMeal(meal.getId()));
        holder.itemView.setOnClickListener(v -> listener.openMealDetails(completeMeal));
        holder.buttonAddIngredients.setText(context.getString(R.string.add_ingredient, completeMeal.getIngredients().size()));
    }


    @Override
    public int getItemCount() {
        return completeMeals.size();
    }

    public void addNewMeal(CompleteMeal minMeal) {
        completeMeals.add(minMeal);
        notifyItemInserted(completeMeals.size());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final ImageView imageViewThumbnail;
        private final ImageView imageViewUser;
        private final Button buttonAddIngredients;
        private final ImageButton imageButtonBookmark;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewMealName);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
            imageViewUser = itemView.findViewById(R.id.imageViewUser);
            imageButtonBookmark = itemView.findViewById(R.id.imageButtonBookmark);
            buttonAddIngredients = itemView.findViewById(R.id.buttonAddIngredients);
        }
    }
}
