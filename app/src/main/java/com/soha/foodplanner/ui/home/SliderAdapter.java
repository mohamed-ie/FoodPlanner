package com.soha.foodplanner.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.bumptech.glide.Glide;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.Meal;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{

    private List<Meal> sliderItems;
    private ViewPager2 viewPager2;

    public SliderAdapter(List<Meal> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent
                .getContext())
                .inflate(R.layout.recycler_view_meal_item
                        ,parent
                        ,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {

        holder.setMealImage(sliderItems.get(position).getPhotoUri());
        holder.setMealName(sliderItems.get(position).getName());

        if(position==sliderItems.size() - 2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder{
        private ImageView mealImage;
        private TextView mealName;


        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);

            mealImage=itemView.findViewById(R.id.item_image);
            mealName=itemView.findViewById(R.id.meal_name);
        }

        void setMealName(String name){

            mealName.setText(name);
        }

        void setMealImage(String img){
            Glide.with(mealImage.getContext()).load(img).into(mealImage);
        }

    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };
}
