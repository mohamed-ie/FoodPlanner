package com.soha.foodplanner.ui.meal_details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.remote.dto.MealsItem;


public class MealDetails extends Fragment {
    TextView mealName,areaName,instructions;
    ImageView mealPhoto;
    YouTubePlayerView mealVideo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MealsItem mealsItem=MealDetailsArgs.fromBundle(getArguments()).getMealArgs();

        initViews(view);
        setMealValues(mealsItem,view);

    }


    private void initViews(View view) {
        mealName=view.findViewById(R.id.meal_detailed_name);
        areaName=view.findViewById(R.id.meal_detailed_area);
        instructions=view.findViewById(R.id.meal_detailed_instructions);
        mealPhoto=view.findViewById(R.id.meal_img);
        mealVideo=view.findViewById(R.id.video);
    }
    private void setMealValues(MealsItem mealsItem,View view){

        mealName.setText(mealsItem.getStrMeal());
        instructions.setText(mealsItem.getStrInstructions());
        areaName.setText(mealsItem.getStrArea());
        setVideo(mealsItem);
        Glide.with(view.getContext()).load(mealsItem.getStrMealThumb()).into(mealPhoto);
    }
    private void setVideo(MealsItem mealsItem){
        getLifecycle().addObserver((LifecycleObserver) mealVideo);
        String[] split = mealsItem.getStrYoutube().split("=");

        mealVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = split[1];
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
    }
}