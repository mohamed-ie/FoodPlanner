package com.soha.foodplanner.ui.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.ui.common.dialogs.confirm.ConfirmDialogFragment;
import com.soha.foodplanner.ui.common.dialogs.loading.LoadingDialogFragment;
import com.soha.foodplanner.ui.common.dialogs.retry.RetryDialogFragment;
import com.soha.foodplanner.ui.common.presenter.Presenter;
import com.soha.foodplanner.ui.common.presenter.factory.PresenterFactory;
import com.soha.foodplanner.ui.common.presenter.store.PresenterStoreImpl;

import java.util.Objects;

public abstract class BaseFragment<P extends Presenter> extends Fragment {
    protected NavController navController;
    protected P presenter;
    private final PresenterFactory<P> factory = PresenterFactory.getInstance(PresenterStoreImpl.getInstance());

    @LayoutRes
    protected abstract int getLayoutResource();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDependencies();
        setupObservers();
    }

    private void setupObservers() {
        getBackStackLiveData(RetryDialogFragment.RETRY)
                .observe(this, (Observer<Boolean>) this::onDialogRetry);

        getBackStackLiveData(LoadingDialogFragment.CANCEL)
                .observe(this, (Observer<Boolean>) this::onDialogLoading);

        getBackStackLiveData(ConfirmDialogFragment.YES)
                .observe(this, (Observer<Boolean>) this::onDialogConfirm);
    }

    protected void onDialogConfirm(Boolean yes) {

    }

    protected void onDialogRetry(boolean retry) {

    }

    protected void onDialogLoading(boolean cancel) {

    }

    protected void initDependencies() {
        navController = NavHostFragment.findNavController(this);
    }

    private void initPresenter() {
        presenter = factory.create(getLayoutResource(), getPresenterFactory());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(getLayoutResource(), container, false);
        initPresenter();
        initViews(view);
        updateUi();
        setListeners();
        return view;
    }

    protected void initViews(View view) {
    }

    protected void updateUi(){

    }
    protected void setListeners() {
    }

    protected abstract Factory<P> getPresenterFactory();

    @Override
    public void onDestroy() {
        factory.onDestroy(getLayoutResource(), requireActivity().isChangingConfigurations());
        super.onDestroy();
    }

    protected LiveData getBackStackLiveData(String key) {
        return Objects.requireNonNull(navController
                        .getCurrentBackStackEntry())
                .getSavedStateHandle()
                .getLiveData(key);

    }
}
