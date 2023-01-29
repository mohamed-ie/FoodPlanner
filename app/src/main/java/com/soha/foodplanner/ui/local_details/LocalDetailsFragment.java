package com.soha.foodplanner.ui.local_details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.soha.foodplanner.MyApp;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.entities.Ingredient;
import com.soha.foodplanner.data.local.entities.IngredientWithMeal;
import com.soha.foodplanner.data.local.entities.Meal;
import com.soha.foodplanner.data.local.model.CompleteIngredient;
import com.soha.foodplanner.data.repository.MealsLocalDataSource;
import com.soha.foodplanner.ui.MainActivity;
import com.soha.foodplanner.ui.local_details.presenter.LocalDetailsListener;
import com.soha.foodplanner.ui.local_details.presenter.LocalDetailsPresenter;
import com.soha.foodplanner.ui.meal_details.IngredientAdapter;

import java.util.ArrayList;
import java.util.List;


public class LocalDetailsFragment extends Fragment implements LocalDetailsListener {

    MealsLocalDataSource repo;

    TextView mealName, mealInstruction, mealArea;
    ImageView mealPhoto;
    YouTubePlayerView mealVideo;
    LocalDetailsPresenter localDetailsPresenter;
    private long mealIdStr;
    private RecyclerView recycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealIdStr = LocalDetailsFragmentArgs.fromBundle(requireArguments()).getMealId();
        //Meal meal=repo.selectMealById(mealIdStr);

        localDetailsPresenter = new LocalDetailsPresenter(this, ((MyApp) ((MainActivity) getHost()).getApplication()).getMealsRepository());
        getLocalMealDetail();

        initViews(view);
        //setMealValues(view,meal);

    }

    private void initViews(View v) {

        mealName = v.findViewById(R.id.meal_detailed_name);
        mealArea = v.findViewById(R.id.meal_detailed_area);
        mealInstruction = v.findViewById(R.id.meal_detailed_instructions);
        mealPhoto = v.findViewById(R.id.meal_img);
        mealVideo = v.findViewById(R.id.video);
        recycler = v.findViewById(R.id.rv_ingredients);

    }

    private void setMealValues(@io.reactivex.rxjava3.annotations.NonNull IngredientWithMeal meal) {
        Meal meal1 = meal.meal;
        mealName.setText(meal1.getName());
        mealArea.setText(meal1.getArea());
        mealInstruction.setText(meal1.getInstructions());
        loadPhoto(meal1.getPhotoUri());
        setVideo(meal1.getVideoUri());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);
        List<CompleteIngredient> completeIngredients = new ArrayList<>();
        for (Ingredient in : meal.ingredients) {
            completeIngredients.add(new CompleteIngredient(in.getName(), in.getPhotoUri(), ""));
        }
        IngredientAdapter ingredientAdapter = new IngredientAdapter(requireContext(), completeIngredients);
        recycler.setAdapter(ingredientAdapter);


    }

    private void loadPhoto(String img) {
        Glide.with(requireContext()).load(img).into(mealPhoto);
    }

    private void setVideo(String url) {
        getLifecycle().addObserver((LifecycleObserver) mealVideo);
        String[] split = url.split("=");

        mealVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {

            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                try {
                    String videoId = split[1];
                    youTubePlayer.loadVideo(videoId, 0);

                } catch (Exception e) {
                    Log.e("TAG", "onReady: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void getLocalMealDetail() {
        localDetailsPresenter.getLocalDetails(mealIdStr);

    }

    @Override
    public void setLocalValues(@io.reactivex.rxjava3.annotations.NonNull IngredientWithMeal meal) {
        setMealValues(meal);

    }
}