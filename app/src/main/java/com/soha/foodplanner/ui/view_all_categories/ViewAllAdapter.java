package com.soha.foodplanner.ui.view_all_categories;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.ui.common.OnInspirationItemListener;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {
    private final List<MinMeal> values;
    private final OnInspirationItemListener onInspirationItemListener;

    public ViewAllAdapter(List<MinMeal> myList, OnInspirationItemListener onInspirationItemListener){
        values=myList;
        this.onInspirationItemListener = onInspirationItemListener;
    }


    @NonNull
    @Override
    public ViewAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycler_view_filter_meal_item,parent,false);
        ViewAllAdapter.ViewHolder vHolder= new ViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MinMeal minMeal=values.get(position);
        holder.mealName.setText(minMeal.getName());
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(VeiwAllCatFragmentDirections
//                        .actionVeiwAllCatFragmentToMealDetails(minMeal.getId(),true));

            }
        });

        Glide.with(holder.itemView).load(minMeal.getThumbnailUrl()).into(holder.mealImage);
        holder.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onInspirationItemListener.addFavouriteMeal(minMeal.getId());
                holder.favIcon.setImageResource(R.drawable.fav_checked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
    protected static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView mealName;
        private final ImageButton favIcon;
        private final FrameLayout itemLayout;
        private final ImageView mealImage;
        public ViewHolder(View v){
            super(v);
            mealName=v.findViewById(R.id.textViewMealName);
            favIcon=v.findViewById(R.id.imageButtonFavourite);
            mealImage=v.findViewById(R.id.imageViewThumbnail);
            itemLayout=v.findViewById(R.id.item_fav_layout);

        }
    }

}


