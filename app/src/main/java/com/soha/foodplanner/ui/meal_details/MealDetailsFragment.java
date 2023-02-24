package com.soha.foodplanner.ui.meal_details;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.soha.foodplanner.MyApp;
import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.local.entities.Meal;
import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.ui.MainActivity;
import com.soha.foodplanner.ui.common.fragment.BaseFragmentWithArgs;
import com.soha.foodplanner.ui.meal_details.presenter.MealDetailsPresenter;
import com.soha.foodplanner.ui.meal_details.presenter.MealDetailsPresenterFactory;

import java.util.Locale;


public class MealDetailsFragment extends BaseFragmentWithArgs<MealDetailsPresenter, MealDetailsFragmentArgs> {

    private TextView textViewCategory;
    private TextView textViewArea;
    private TextView textViewMealName;
    private TextView textViewChefName;
    private TextView textViewRatingValue;
    private TextView textViewNumberOfRatings;
    private TextView textViewInstructions;
    private TextView textViewViewAllComments;
    private TextView textViewViewAllFromSameChef;
    private TextView textViewViewAllFromSameCategory;
    private TextView textViewViewAllFromSameArea;

    private RecyclerView recyclerViewIngredients;
    private RecyclerView recyclerViewComments;
    private RecyclerView recyclerViewSameChefMeals;
    private RecyclerView recyclerViewSameCategoryMeals;
    private RecyclerView recyclerViewSameAreaMeals;

    private ImageView imageViewArea;
    private ImageView imageViewThumbnail;
    private YouTubePlayerView youTubePlayerView;


    private IngredientsAdapter adapterIngredients;

    @Override

    protected int getLayoutResource() {
        return R.layout.fragment_meal_details;
    }


    @Override
    public MealDetailsFragmentArgs getSafeArgs() {
        return MealDetailsFragmentArgs.fromBundle(requireArguments());
    }

    @Override
    protected Factory<MealDetailsPresenter> getPresenterFactory() {
        return new MealDetailsPresenterFactory(((MyApp) ((MainActivity) requireHost()).getApplication()).getMealsRepository());
    }

    @Override
    protected void initViews(View view) {
        textViewCategory = view.findViewById(R.id.textViewCategory);
        textViewArea = view.findViewById(R.id.textViewArea);
        textViewMealName = view.findViewById(R.id.textViewMealName);
        textViewChefName = view.findViewById(R.id.textViewChefName);
        textViewRatingValue = view.findViewById(R.id.textViewRatingValue);
        textViewNumberOfRatings = view.findViewById(R.id.textViewNumberOfRatings);
        textViewInstructions = view.findViewById(R.id.textViewInstructions);
        textViewViewAllComments = view.findViewById(R.id.textViewViewAllComments);
        textViewViewAllFromSameChef = view.findViewById(R.id.textViewViewAllFromSameChef);
        textViewViewAllFromSameCategory = view.findViewById(R.id.textViewViewAllFromSameCategory);
        textViewViewAllFromSameArea = view.findViewById(R.id.textViewViewAllFromSameArea);

        recyclerViewComments = view.findViewById(R.id.recyclerViewComments);
        recyclerViewSameChefMeals = view.findViewById(R.id.recyclerViewSameChefMeals);
        recyclerViewSameCategoryMeals = view.findViewById(R.id.recyclerViewSameCategoryMeals);
        recyclerViewSameAreaMeals = view.findViewById(R.id.recyclerViewSameAreaMeals);

        imageViewArea = view.findViewById(R.id.imageViewArea);
        imageViewThumbnail = view.findViewById(R.id.imageViewThumbnail);

        youTubePlayerView = view.findViewById(R.id.youtubePlayerView);
        initIngredientsRecyclerView(view);
    }



    private void initIngredientsRecyclerView(View view) {
        recyclerViewIngredients = view.findViewById(R.id.recyclerViewIngredients);
        adapterIngredients = new IngredientsAdapter();
        recyclerViewIngredients.setAdapter(adapterIngredients);
    }


    @Override
    protected void updateUi() {
        CompleteMeal completeMeal = args.getCompleteMeal();
        Meal meal = completeMeal.getMeal();
        Glide.with(this)
                .load(meal.getPhotoUri())
                .into(imageViewThumbnail);

        textViewMealName.setText(meal.getName());
        textViewArea.setText(meal.getArea());
        textViewCategory.setText(meal.getCategory());
        textViewInstructions.setText(meal.getInstructions());

        int resource = getResources()
                .getIdentifier(meal.getArea().toLowerCase(Locale.ROOT),
                        "drawable",
                        requireContext().getPackageName());
        if (resource == 0)
            resource = R.drawable.unknown_area;

        Glide.with(this)
                .load(resource)
                .override(imageViewArea.getWidth(), imageViewArea.getHeight())
                .circleCrop()
                .into(imageViewArea);

        adapterIngredients.setIngredients(completeMeal.getIngredients());

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(meal.getVideoId(), 0);
            }
            @Override
            public void onError(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerError error) {
                youTubePlayerView.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        youTubePlayerView.release();
    }

}