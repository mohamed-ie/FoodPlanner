package com.soha.foodplanner.ui.meal_details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.data_source.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.data_source.remote.webservice.Webservice;
import com.soha.foodplanner.data.dto.meal.MealDto;
import com.soha.foodplanner.data.dto.meal.MealsItem;
import com.soha.foodplanner.data.local.MealDAO;
import com.soha.foodplanner.ui.meal_details.presenter.MealDetailsListener;
import com.soha.foodplanner.ui.meal_details.presenter.MealDetailsPresenter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealDetailsFragment extends Fragment implements MealDetailsListener {
    TextView mealName,areaName,instructions;
    ImageView mealPhoto;
    YouTubePlayerView mealVideo;
    TheMealDBWebService theMealDBWebService;
    private Button planButton;
    protected NavController navController;
    private MealDetailsPresenter mealDetailsPresenter;
    private long mealIdStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(MealDetailsFragment.this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealIdStr=MealDetailsFragmentArgs.fromBundle(requireArguments()).getMealId();


        theMealDBWebService = Webservice.getInstance().getTheMealDBWebService();
        mealDetailsPresenter=new MealDetailsPresenter(theMealDBWebService,this,view);
        getMealDetail();


        initViews(view);
        planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(MealDetailsFragmentDirections.actionMealDetailsToDayOfWeekFragment());

            }
        });



    }


    private void initViews(View view) {
        mealName=view.findViewById(R.id.meal_detailed_name);
        areaName=view.findViewById(R.id.meal_detailed_area);
        instructions=view.findViewById(R.id.meal_detailed_instructions);
        mealPhoto=view.findViewById(R.id.meal_img);
        mealVideo=view.findViewById(R.id.video);
        planButton=view.findViewById(R.id.plan_btn);

    }
    private void setMealValues(MealsItem mealsItem, View view){

        mealName.setText(mealsItem.getStrMeal());
        Log.e("error",mealsItem.getStrMeal()+"");
        instructions.setText(mealsItem.getStrInstructions());
        areaName.setText(mealsItem.getStrArea());
        if(mealsItem.getStrYoutube()!=null){
            setVideo(mealsItem);
        }

        Glide.with(view.getContext()).load(mealsItem.getStrMealThumb()).into(mealPhoto);
    }
    private void setVideo(MealsItem mealsItem){
        getLifecycle().addObserver((LifecycleObserver) mealVideo);
        String[] split = mealsItem.getStrYoutube().split("=");

        mealVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {

            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                try{
                    String videoId = split[1];
                    youTubePlayer.loadVideo(videoId, 0);

                }catch (Exception e){
                    Log.e("TAG", "onReady: "+ e.getMessage() );
                }

            }
        });
    }

    @Override
    public void getMealDetail() {
        mealDetailsPresenter.getDetails(mealIdStr);

    }

    @Override
    public void setValues(MealsItem mealsItem,View view) {
        setMealValues(mealsItem,view);
    }
}