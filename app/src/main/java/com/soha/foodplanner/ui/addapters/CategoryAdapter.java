package com.soha.foodplanner.ui.addapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.Meal;
import com.soha.foodplanner.ui.home.SliderAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private  final Context context;
    private String categoryName;
    private List<List<Meal>> meals;
    private static final String Tag="Recycler";
    private ViewPager2 viewPager2;
    private List<Meal> randomMeals;


    public CategoryAdapter(Context con,String categName,List<List<Meal>> myList){
        context=con;
        categoryName=categName;
        meals=myList;

    }

    @Override
    public int getItemViewType(int position) {
        return position==0?R.layout.slider_item:R.layout.recycler_view_category_list_item;
        /*
        if(position==0){
            return R.layout.slider_item;
        }else {
            return R.layout.recycler_view_category_list_item;
        }
        return super.getItemViewType(position);*/
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(viewType,parent,false);
        CategoryAdapter.ViewHolder vHolder=new ViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if(position==0){
                holder.random.setText(categoryName);
                holder.recyclerView.setAdapter(new SliderAdapter(randomMeals,viewPager2));
            }else{
                holder.mealCategoryName.setText(categoryName);
                holder.recyclerView.setAdapter(new MealAdapter(meals.get(position)));

            }


    }


    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mealCategoryName;
        public RecyclerView recyclerView;
        public ConstraintLayout constraintLayout;
        public View layout;
        public TextView random;

        public ViewHolder(View v){
            super(v);
            layout=v;
            random=v.findViewById(R.id.random_meal);
            viewPager2=v.findViewById(R.id.view_pager);
            mealCategoryName=v.findViewById(R.id.category_meal);
            recyclerView=v.findViewById(R.id.recycler_category);
            constraintLayout=v.findViewById(R.id.all_category_view);

            viewPager2.setClipToPadding(false);
            viewPager2.setClipChildren(false);
            viewPager2.setOffscreenPageLimit(3);
            viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

            CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
            compositePageTransformer.addTransformer(new MarginPageTransformer(40));
            compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                @Override
                public void transformPage(@NonNull View page, float position) {
                    float r = 1-Math.abs(position);
                    page.setScaleY(0.85f + r + 0.15f);
                }
            });

            viewPager2.setPageTransformer(compositePageTransformer);
            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
//                sliderHandler.removeCallbacks(sliderRunnable);
//                sliderHandler.postDelayed(sliderRunnable,3000);
                    //
                }
            });

            //recyclerView.setAdapter(new CategoryAdapter());



            Disposable disposable= Flowable.intervalRange(0,1,0,10, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Throwable {
                            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
                        }
                    });

        }


    }

}
