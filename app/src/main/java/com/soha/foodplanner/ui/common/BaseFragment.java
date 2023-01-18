package com.soha.foodplanner.ui.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.ui.common.presenter.Presenter;
import com.soha.foodplanner.ui.common.presenter.factory.PresenterFactory;
import com.soha.foodplanner.ui.common.presenter.factory.PresenterFactoryImpl;
import com.soha.foodplanner.ui.common.presenter.store.PresenterStoreImpl;

public abstract class BaseFragment<P extends Presenter> extends Fragment {
    protected NavController navController;
    protected P presenter;

    @LayoutRes
    protected abstract int getLayoutResource();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        initPresenter();
    }

    private void initPresenter() {
        PresenterFactory<P> factory = PresenterFactoryImpl.getInstance(PresenterStoreImpl.getInstance());
        presenter = factory.create(getTag(), getPresenterFactory());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(getLayoutResource(), container, false);
        initViews(view);
        setListeners();
        return view;
    }

    protected void initViews(View view) {
    }

    protected void setListeners() {
    }

    protected abstract Factory<P> getPresenterFactory();

}
