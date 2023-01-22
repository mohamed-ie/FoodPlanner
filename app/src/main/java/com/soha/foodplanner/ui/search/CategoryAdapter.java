package com.soha.foodplanner.ui.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soha.foodplanner.R;

import java.util.List;
import java.util.Locale;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<String> categories;
    private final OnCategoryItemClickListener listener;
    private Context context;

    public CategoryAdapter(List<String> categories, OnCategoryItemClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCategories(List<String> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        return new ViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.recycler_view_ingredient_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        String category = categories.get(position);
        int resource = context
                .getResources()
                .getIdentifier(categories.get(position).toLowerCase(Locale.ROOT),
                        "drawable",
                        context.getPackageName());
        if (resource == 0)
            resource = R.drawable.unknown_meal;
        holder.imageViewThumbnail.setImageResource(resource);
        holder.textViewName.setText(category);

        holder.textViewName.setOnClickListener(v -> listener.onCategoryClick(category));
        holder.imageViewThumbnail.setOnClickListener(v -> listener.onCategoryClick(category));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final ImageView imageViewThumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
        }
    }
}
