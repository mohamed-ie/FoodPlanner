package com.soha.foodplanner.ui.meal_details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.model.CompleteIngredient;
import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.ui.planned.PlannedAdapter;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder>{
    private List<CompleteIngredient> ingrList;
    private Context context;
    public IngredientAdapter(Context context,List<CompleteIngredient>ingrList){
        this.context=context;
        this.ingrList=ingrList;
    }
    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.detailed_ingredient_item,parent,false);
        return new IngredientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
        holder.ingName.setText(ingrList.get(position).getName());
        Glide.with(context).load(ingrList.get(position).getThumbnailUrl())
                .placeholder(R.drawable.ic_launcher_foreground).into(holder.ingImg);

        holder.ingMeasure.setText(ingrList.get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        return ingrList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView ingName;
        TextView ingMeasure;
        ImageView ingImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingImg=itemView.findViewById(R.id.ingredient_img);
            ingName=itemView.findViewById(R.id.ingredient_name);
            ingMeasure=itemView.findViewById(R.id.ingredient_messure);

        }
    }
}
