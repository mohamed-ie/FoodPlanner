package com.soha.foodplanner.ui.main.favourite;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.entities.FavouriteMealsWithMeal;
import com.soha.foodplanner.ui.main.favourite.presenter.FavouritePresenterListener;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    private List<FavouriteMealsWithMeal> values;
    private static final String Tag="Recycler";
    Context context;
    private final FavouritePresenterListener favouritePresenterListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycler_view_filter_meal_item,parent,false);
        ViewHolder vHolder=new ViewHolder(view);
        context=parent.getContext();
        Log.i(Tag,"onCreateViewHolder");

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FavouriteMealsWithMeal favouriteMealsWithMeal=values.get(position);
        holder.mealName.setText(favouriteMealsWithMeal.getMeal().getName());
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(com.soha.foodplanner.ui.favourite.FavouriteFragmentDirections
//                        .actionFavouriteFragmentToLocalDetailsFragment(favouriteMealsWithMeal.getMeal().getId()));

            }
        });

        Glide.with(holder.mealImage.getContext()).load(favouriteMealsWithMeal.getMeal().getPhotoUri()).into(holder.mealImage);
        holder.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favouritePresenterListener.deleteMealFromFav(favouriteMealsWithMeal);

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
            mealName=v.findViewById(R.id.textViewMealName);
            favIcon=v.findViewById(R.id.imageButtonFavourite);
            favIcon.setImageResource(R.drawable.fav_checked);
            mealImage=v.findViewById(R.id.imageViewThumbnail);
            itemLayout=v.findViewById(R.id.item_fav_layout);

        }


    }
    public FavAdapter(List<FavouriteMealsWithMeal> myList, FavouritePresenterListener favouritePresenterListener){
        values=myList;


        this.favouritePresenterListener = favouritePresenterListener;
    }
}


