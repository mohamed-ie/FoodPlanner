package com.soha.foodplanner.ui.addapters;

import android.content.Context;
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
import java.util.List;


public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {
    private List<Meal> values;
    private static final String Tag="Recycler";
    private OnItemClickListener<Meal> onItemClickListener;


    public MealAdapter(List<Meal> myList){
        values=myList;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycler_view_meal_item,parent,false);
        //Glide.with(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal mealsItem=values.get(position);
        holder.mealName.setText(mealsItem.getName());
        Glide.with(holder.itemView)
                .load(values.get(position).getPhotoUri())
                .into(holder.mealImage);
        holder.constraintLayout.setOnClickListener(v->
                onItemClickListener.onClick(values.get(position))
        );
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
