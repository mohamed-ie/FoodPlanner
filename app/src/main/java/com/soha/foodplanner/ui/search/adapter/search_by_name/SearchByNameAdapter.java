package com.soha.foodplanner.ui.search.adapter.search_by_name;

import android.annotation.SuppressLint;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soha.foodplanner.R;
import com.soha.foodplanner.ui.search.OnSearchItemClickListener;

import java.util.List;

public class SearchByNameAdapter extends RecyclerView.Adapter<SearchByNameAdapter.ViewHolder> {
    private List<Pair<Long, String>> names;
    private final OnSearchItemClickListener listener;

    public SearchByNameAdapter(List<Pair<Long, String>> names, OnSearchItemClickListener listener) {
        this.names = names;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNames(List<Pair<Long, String>> names) {
        this.names = names;
        notifyDataSetChanged();
    }

    public List<Pair<Long, String>> getNames() {
        return names;
    }

    @NonNull
    @Override
    public SearchByNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycler_view_search_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchByNameAdapter.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v->listener.onClick(names.get(position)));
        holder.textViewName.setText(names.get(position).second);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.editTextName);
        }
    }
}
