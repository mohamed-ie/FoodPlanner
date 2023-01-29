package com.soha.foodplanner.ui.meal_details;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.soha.foodplanner.MyApp;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.data_source.remote.meals.MealsRemoteDataSource;
import com.soha.foodplanner.data.data_source.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.data_source.remote.webservice.Webservice;
import com.soha.foodplanner.data.dto.meal.MealDto;
import com.soha.foodplanner.data.dto.meal.MealsItem;
import com.soha.foodplanner.data.local.MealDAO;
import com.soha.foodplanner.data.local.entities.Meal;
import com.soha.foodplanner.data.local.model.CompleteIngredient;
import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.ui.MainActivity;
import com.soha.foodplanner.ui.meal_details.presenter.MealDetailsListener;
import com.soha.foodplanner.ui.meal_details.presenter.MealDetailsPresenter;

import org.jetbrains.annotations.Contract;

import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealDetailsFragment extends Fragment implements MealDetailsListener {
    TextView mealName, areaName, instructions;
    DatePickerDialog.OnDateSetListener setListener;
    String dayName;
    ImageView mealPhoto;
    YouTubePlayerView mealVideo;
    TheMealDBWebService theMealDBWebService;
    private Button planButton, calenderButton;
    protected NavController navController;
    private MealDetailsPresenter mealDetailsPresenter;
    private long mealIdStr;

    RecyclerView ingredientsRV;

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

        mealIdStr = MealDetailsFragmentArgs.fromBundle(requireArguments()).getMealId();


        theMealDBWebService = Webservice.getInstance().getTheMealDBWebService();
        mealDetailsPresenter = new MealDetailsPresenter(((MyApp) ((MainActivity) requireHost()).getApplication()).getMealsRepository(), this);
        getMealDetail();


        initViews(view);

        calenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, mealName.getText());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, instructions.getText());
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, areaName.getText());
                intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {

                    startActivity(intent);
                } else {
                    Toast.makeText(requireContext(), "There is no app that support this action", Toast.LENGTH_SHORT).show();
                }

            }
        });


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int dayVal = calendar.get(Calendar.DAY_OF_WEEK);

        planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navController.navigate(MealDetailsFragmentDirections.actionMealDetailsToDayOfWeekFragment());
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , setListener, year, month, day);


                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                //  month=datePickerDialog.getDatePicker().getMonth();
            }

        });

        String mealTime = "breakfast";
        Calendar selectCalendar = Calendar.getInstance();
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectCalendar.set(year, month, dayOfMonth);
                selectCalendar.getTime().getTime();
                int dayVal = selectCalendar.get(Calendar.DAY_OF_WEEK);

                switch (dayVal) {
                    case 1:
                        dayName = "Sunday";
                        break;
                    case 2:
                        dayName = "Monday";
                        break;
                    case 3:
                        dayName = "Tuesday";
                        break;
                    case 4:
                        dayName = "Wednesday";
                        break;
                    case 5:
                        dayName = "Thursday";
                        break;
                    case 6:
                        dayName = "Friday";
                        break;
                    case 7:
                        dayName = "Saturday";
                        break;

                }
                Toast.makeText(requireContext(), dayName, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(requireContext());
                //   builderSingle.setIcon(R.drawable.ic_favorite);
                builderSingle.setTitle("Select day:-");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("Breakfast");
                arrayAdapter.add("Launch");
                arrayAdapter.add("Dinner");

                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(requireContext());
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected ");
                        builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mealDetailsPresenter.addToPlanned(calendar.getTime().getTime(), mealTime);
                                dialog.dismiss();
                            }
                        });
                        builderInner.show();
                    }
                });
                builderSingle.show();
            }

        };


    }


    private void initViews(View view) {
        mealName = view.findViewById(R.id.meal_detailed_name);
        areaName = view.findViewById(R.id.meal_detailed_area);
        instructions = view.findViewById(R.id.meal_detailed_instructions);
        mealPhoto = view.findViewById(R.id.meal_img);
        mealVideo = view.findViewById(R.id.video);
        planButton = view.findViewById(R.id.plan_btn);
        ingredientsRV = view.findViewById(R.id.rv_ingredients);
        calenderButton = view.findViewById(R.id.calender_btn);
    }

    private void setMealValues(Meal mealsItem, View view) {

        mealName.setText(mealsItem.getName());
        instructions.setText(mealsItem.getInstructions());
        areaName.setText(mealsItem.getArea());
        if (mealsItem.getVideoUri() != null) {
            setVideo(mealsItem);
        }

        Glide.with(view.getContext()).load(mealsItem.getPhotoUri()).into(mealPhoto);
    }

    private void setVideo(Meal mealsItem) {
        getLifecycle().addObserver((LifecycleObserver) mealVideo);
        String[] split = mealsItem.getVideoUri().split("=");

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
    public void getMealDetail() {
        mealDetailsPresenter.getDetails(mealIdStr);
    }

    @Override
    public void setValues(CompleteMeal mealsItem) {
        setMealValues(mealsItem.getMeal(), requireView());
        setIngredients(mealsItem.getIngredients(), requireView());
    }

    @Override
    public void setIngredients(List<CompleteIngredient> mealsItem, View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        ingredientsRV.setLayoutManager(linearLayoutManager);
        IngredientAdapter ingredientAdapter = new IngredientAdapter(view.getContext(), mealsItem);
        ingredientsRV.setAdapter(ingredientAdapter);
    }
}