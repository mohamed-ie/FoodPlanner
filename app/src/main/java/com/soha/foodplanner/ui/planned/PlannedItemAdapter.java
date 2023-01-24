package com.soha.foodplanner.ui.planned;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.Meal;
import com.soha.foodplanner.data.model.CompleteMeal;
import com.soha.foodplanner.ui.addapters.OnItemClickListener;

import java.util.List;

public class PlannedItemAdapter extends RecyclerView.Adapter<PlannedItemAdapter.ViewHolder> {
    private List<CompleteMeal> values;
    private static final String Tag="Recycler";
    private OnItemClickListener<CompleteMeal> onItemClickListener;


    public PlannedItemAdapter(List<CompleteMeal> myList){
        values=myList;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycler_view_meal_item
                ,parent
                ,false);
        //Glide.with(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CompleteMeal meal=values.get(position);
        holder.mealName.setText(meal.getMeal().getName());
        Glide.with(holder.itemView)
                .load(values.get(position).getMeal().getPhotoUri())
                .into(holder.mealImage);


        Log.i(Tag,"onBindViewHolder");

    }


    @Override
    public int getItemCount() {
        return values.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mealName;
        public ImageView mealImage;
        public ConstraintLayout constraintLayout;
        public View layout;
        public ViewHolder(View v){
            super(v);
            layout=v;
            mealName=v.findViewById(R.id.textViewName);
            mealImage=v.findViewById(R.id.imageViewThumbnail);
//            constraintLayout=v.findViewById(R.id.total_view);

        }


    }

}

