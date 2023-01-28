package com.soha.foodplanner.ui.search.adapter.area;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.soha.foodplanner.R;

import java.util.List;
import java.util.Locale;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {
    private List<String> areas;
    private final OnAreaItemClickListener listener;
    private Context context;

    public AreaAdapter(List<String> areas, OnAreaItemClickListener listener) {
        this.areas = areas;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setAreas(List<String> areas) {
        this.areas = areas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AreaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        return new ViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.recycler_view_category_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AreaAdapter.ViewHolder holder, int position) {
        String area = areas.get(position);
        int resource = context
                .getResources()
                .getIdentifier(areas.get(position).toLowerCase(Locale.ROOT),
                        "drawable",
                        context.getPackageName());
        if (resource == 0)
            resource = R.drawable.unknown_area;
        Glide.with(holder.itemView)
                .load(resource)
                .circleCrop()
                .into(holder.imageViewThumbnail);
        holder.textViewName.setText(area);

        holder.textViewName.setOnClickListener(v -> listener.onAreaItemClick(area));
        holder.imageViewThumbnail.setOnClickListener(v -> listener.onAreaItemClick(area));
    }

    @Override
    public int getItemCount() {
        return areas.size();
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
