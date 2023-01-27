package com.soha.foodplanner.ui.view_all_categories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.repository.Repository;
import com.soha.foodplanner.ui.common.AddToFavourite;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {
    private List<MinMeal> values;
    private static final String Tag="Recycler";
    private final AddToFavourite addToFavourite;
    Repository rep;
    Context context;

    @NonNull
    @Override
    public ViewAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycler_view_filter_meal_item,parent,false);
        ViewAllAdapter.ViewHolder vHolder=new ViewHolder(view);
        context=parent.getContext();
        Log.i(Tag,"onCreateViewHolder");

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MinMeal minMeal=values.get(position);
        holder.mealName.setText(minMeal.getName());
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(VeiwAllCatFragmentDirections
                        .actionVeiwAllCatFragmentToMealDetails(String.valueOf(minMeal.getId())));

            }
        });

        Glide.with(holder.mealImage.getContext()).load(minMeal.getThumbnailUrl()).into(holder.mealImage);
        holder.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rep=new Repository(context);
                addToFavourite.addFavouriteMeal(minMeal);
                holder.favIcon.setImageResource(R.drawable.fav_checked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mealName;
        private ImageButton favIcon;
        private FrameLayout itemLayout;
        private ImageView mealImage;
        public ViewHolder(View v){
            super(v);
            mealName=v.findViewById(R.id.textViewName);
            favIcon=v.findViewById(R.id.imageButtonFavourite);
            mealImage=v.findViewById(R.id.imageViewThumbnail);
            itemLayout=v.findViewById(R.id.item_fav_layout);

        }


    }
    public ViewAllAdapter(List<MinMeal> myList, AddToFavourite addToFavourite){
        values=myList;


        this.addToFavourite = addToFavourite;
    }
}


