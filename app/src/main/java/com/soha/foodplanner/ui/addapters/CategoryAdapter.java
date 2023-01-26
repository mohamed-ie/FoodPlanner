package com.soha.foodplanner.ui.addapters;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.viewpager2.widget.CompositePageTransformer;
//import androidx.viewpager2.widget.MarginPageTransformer;
//import androidx.viewpager2.widget.ViewPager2;
//
//import com.soha.foodplanner.R;
//import com.soha.foodplanner.data.dto.min_meal.MinMealDto;
//import com.soha.foodplanner.data.local.model.MinMeal;
//import com.soha.foodplanner.ui.home.SliderAdapter;
//
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
//import io.reactivex.rxjava3.core.Flowable;
//import io.reactivex.rxjava3.disposables.Disposable;
//import io.reactivex.rxjava3.functions.Consumer;
//import io.reactivex.rxjava3.schedulers.Schedulers;
//
//public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
//
//    private String categoryName;
//    private List<MinMealDto> meals;
//    private static final String Tag="Recycler";
//    private ViewPager2 viewPager2;
//
//
//
//    public CategoryAdapter(String categName,List<MinMealDto> myList){
//        categoryName=categName;
//        meals=myList;
//
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position==0?R.layout.slider_item:R.layout.recycler_view_category_list_item;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
//        View view=inflater.inflate(viewType,parent,false);
//        CategoryAdapter.ViewHolder vHolder=new ViewHolder(view);
//        return vHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        Log.e("TAG", "onBindViewHolder: "+ meals.size() );
//            if(position==0){
//                holder.random.setText(categoryName);
//                viewPager2.setAdapter(new SliderAdapter(meals.get(0),viewPager2));
//            }
//            else {
//                holder.mealCategoryName.setText(categoryName);
//                holder.recyclerView.setAdapter(new MealAdapter(meals.get(position)));
//
//            }
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return meals.size();
//    }
//
//    public void addNewCategory(List<MinMeal> minMeals) {
//
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//
//        private TextView mealCategoryName;
//        private RecyclerView recyclerView;
//        private View layout;
//        private TextView random;
//
//        public ViewHolder(View v){
//            super(v);
//
//            initViews(v);
//            viewPagerSetUp();
//            switchViewPagerItem();
//
//        }
//        private void switchViewPagerItem(){
//            Disposable disposable= Flowable
//                    .interval(0,4, TimeUnit.SECONDS)
//                    .subscribeOn(Schedulers.computation())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Long>() {
//                        @Override
//                        public void accept(Long aLong) throws Throwable {
//                            if(viewPager2.getCurrentItem()<meals.size()){
//                                viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
//
//                            }else{
//                                viewPager2.setCurrentItem(0);
//
//                            }
//                        }
//                    });
//        }
//        private void initViews(View v){
//
//            layout=v;
//            random=v.findViewById(R.id.tv_meal_category);
//            viewPager2=v.findViewById(R.id.recycler_pager);
//            mealCategoryName=v.findViewById(R.id.category_meal);
//            recyclerView=v.findViewById(R.id.recycler_category_item);
//        }
//        private void viewPagerSetUp(){
//            viewPager2.setClipToPadding(false);
//            viewPager2.setClipChildren(false);
//            viewPager2.setOffscreenPageLimit(3);
//            viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
//
//            CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
//            compositePageTransformer.addTransformer(new MarginPageTransformer(20));
//            compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
//                @Override
//                public void transformPage(@NonNull View page, float position) {
//                    float r = 1-Math.abs(position);
//                    page.setScaleY(0.65f+ 0.25f*r + 0.15f);
//                }
//            });
//
//            viewPager2.setPageTransformer(compositePageTransformer);
//        }
//
//
//    }
//
//}

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.ui.home.CategoryWithMeals;
import com.soha.foodplanner.ui.home.SliderAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<CategoryWithMeals> categoryWithMealsList;

    public CategoryAdapter(List<CategoryWithMeals> categoryWithMealsList) {
        this.categoryWithMealsList = categoryWithMealsList;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? R.layout.slider_item : R.layout.recycler_view_category_list_item;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(viewType, parent, false);
        CategoryAdapter.ViewHolder vHolder = new ViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryWithMeals categoryWithMeals = categoryWithMealsList.get(position);
        holder.mealCategoryName.setText(categoryWithMeals.getName());
        if (position == 0) {
            ViewPager2 viewPager2 = ((ViewPager2) holder.recyclerView);
            viewPager2.setAdapter(new SliderAdapter(categoryWithMeals.getMeals()));
            holder.viewPagerSetUp(viewPager2);
            holder.switchViewPagerItem(viewPager2, categoryWithMeals.getMeals().size());
        } else {
            ((RecyclerView) holder.recyclerView).setAdapter(new MealAdapter(categoryWithMeals.getMeals()));
        }
    }


    @Override
    public int getItemCount() {
        return categoryWithMealsList.size();
    }

    public void addNewCategory(List<MinMeal> minMeals, String hh) {
        categoryWithMealsList.add(new CategoryWithMeals(hh, minMeals));
        notifyItemInserted(categoryWithMealsList.size());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mealCategoryName;
        private Disposable disposable;
        private final View recyclerView;

        public ViewHolder(View v) {
            super(v);
            mealCategoryName = v.findViewById(R.id.tv_meal_category);
            recyclerView = v.findViewById(R.id.recyclerView);
        }

        private void switchViewPagerItem(ViewPager2 viewPager2, int count) {
            if (disposable == null)
                disposable = Flowable.interval(0, 4, TimeUnit.SECONDS).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        if (viewPager2.getCurrentItem() < count) {
                            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                        } else {
                            viewPager2.setCurrentItem(0);
                        }
                    }
                });
        }


        //
        private void viewPagerSetUp(ViewPager2 viewPager2) {
            viewPager2.setClipToPadding(false);
            viewPager2.setClipChildren(false);
            viewPager2.setOffscreenPageLimit(3);
            viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

            CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
            compositePageTransformer.addTransformer(new MarginPageTransformer(20));
            compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                @Override
                public void transformPage(@NonNull View page, float position) {
                    float r = 1 - Math.abs(position);
                    page.setScaleY(0.65f + 0.25f * r + 0.15f);
                }
            });
            viewPager2.setPageTransformer(compositePageTransformer);
        }

    }
}
