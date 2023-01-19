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

import com.soha.foodplanner.R;
import com.soha.foodplanner.data.local.Meal;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private  final Context context;
    private String categoryName;
    private List<List<Meal>> values;
    private static final String Tag="Recycler";


    public CategoryAdapter(Context con,String categName,List<List<Meal>> myList){
        context=con;
        categoryName=categName;
        values=myList;

    }

    @Override
    public int getItemViewType(int position) {
        //return position==0?R.layout.category_list_item:R.layout.category_list_item;
        if(position==0){
           // return R.layout.inspiration_item
        }else {
            return R.layout.recycler_view_category_list_item;
        }
        return super.getItemViewType(position);
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

        holder.mealCategoryName.setText(categoryName);
        holder.recyclerView.setAdapter(new MealAdapter(values.get(position)));

    }


    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mealCategoryName;
        public RecyclerView recyclerView;
        public ConstraintLayout constraintLayout;
        public View layout;
        public ViewHolder(View v){
            super(v);
            layout=v;
            mealCategoryName=v.findViewById(R.id.category_meal);
            recyclerView=v.findViewById(R.id.recycler_category);
            constraintLayout=v.findViewById(R.id.all_category_view);

        }


    }

}
