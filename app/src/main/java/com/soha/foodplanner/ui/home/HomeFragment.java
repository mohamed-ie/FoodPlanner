package com.soha.foodplanner.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.soha.foodplanner.R;
import com.soha.foodplanner.data.mapper.CategoryMapper;
import com.soha.foodplanner.data.mapper.Mapper;
import com.soha.foodplanner.data.remote.dto.category.CategoryDto;
import com.soha.foodplanner.data.remote.webservice.TheMealDBWebService;
import com.soha.foodplanner.data.remote.webservice.Webservice;
import com.soha.foodplanner.ui.addapters.CategoryAdapter;
import com.soha.foodplanner.ui.search.OnCategoryItemClickListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomeFragment extends Fragment implements OnCategoryItemClickListener {
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    private ViewPager2 viewPager2;
    List<String> categoryItemList=new ArrayList<String>();


    @SuppressLint("CheckResult")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TheMealDBWebService theMealDBWebService= Webservice.getInstance().getTheMealDBWebService();
        Single<CategoryDto> single=theMealDBWebService.getAllCategories();
        single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<CategoryDto>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        Log.i("sohaSoha","OnSubscribe");
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CategoryDto categoryDto) {
                        CategoryMapper categoryMapper=new CategoryMapper();
                        categoryItemList=categoryMapper.map(categoryDto);
                        Log.i("sohaSoha",categoryItemList.toString());

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.i("sohaSoha","OnError");
                    }
                });



        recyclerView=view.findViewById(R.id.recycler);
        viewPager2=view.findViewById(R.id.view_pager);



    }

    private Runnable sliderRunnable= new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);

        }

    };

    @Override
    public void onPause() {
        super.onPause();
//        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
//        sliderHandler.postDelayed(sliderRunnable,3000);
    }

    @Override
    public void onCategoryClick(String category) {

    }
}