package com.soha.foodplanner.ui.search.adapter.search_by_name;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soha.foodplanner.R;

import java.util.List;

public class SearchByNameAdapter extends RecyclerView.Adapter<SearchByNameAdapter.ViewHolder> {
    private List<String> names;

    public SearchByNameAdapter(List<String> names) {
        this.names = names;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNames(List<String> names) {
        this.names = names;
        notifyDataSetChanged();
    }

    public List<String> getNames() {
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
        holder.textViewName.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
        }
    }
}
