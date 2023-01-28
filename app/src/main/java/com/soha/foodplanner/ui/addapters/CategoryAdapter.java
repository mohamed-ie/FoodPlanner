package com.soha.foodplanner.ui.addapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.ui.common.AddToFavourite;
import com.soha.foodplanner.ui.filter.MealsFilterFragment;
import com.soha.foodplanner.ui.filter.MealsFilterFragmentDirections;
import com.soha.foodplanner.ui.home.CategoryWithMeals;
import com.soha.foodplanner.ui.home.HomeFragment;
import com.soha.foodplanner.ui.home.HomeFragmentDirections;
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
    private final AddToFavourite addToFavourite;
    Context context;


    public CategoryAdapter(List<CategoryWithMeals> categoryWithMealsList, AddToFavourite addToFavourite) {
        this.categoryWithMealsList = categoryWithMealsList;
        this.addToFavourite = addToFavourite;
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
        context=parent.getContext();
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryWithMeals categoryWithMeals = categoryWithMealsList.get(position);
        holder.mealCategoryName.setText(categoryWithMeals.getName());
        if (position == 0) {
            ViewPager2 viewPager2 = ((ViewPager2) holder.recyclerView);
            viewPager2.setAdapter(new SliderAdapter(categoryWithMeals.getMeals(), addToFavourite));
            holder.viewPagerSetUp(viewPager2);
            holder.switchViewPagerItem(viewPager2, categoryWithMeals.getMeals().size());
        } else {
            ((RecyclerView) holder.recyclerView).setAdapter(new MealAdapter(categoryWithMeals.getMeals(), addToFavourite));
            holder.viewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(com.soha.foodplanner.ui.home.HomeFragmentDirections
                            .actionHomeFragmentToVeiwAllCatFragment(categoryWithMeals.getName()));
                }
            });

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

        private final TextView mealCategoryName,viewAll;
        private Disposable disposable;
        private final View recyclerView;

        public ViewHolder(View v) {
            super(v);
            mealCategoryName = v.findViewById(R.id.tv_meal_category);
            recyclerView = v.findViewById(R.id.recyclerView);
            viewAll=v.findViewById(R.id.view_all);

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
