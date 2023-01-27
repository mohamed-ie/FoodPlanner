package com.soha.foodplanner.ui.common.observers;

import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.ui.search.presenter.AllIngredientsListener;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class AllIngredientsObserver  implements SingleObserver<List<MinIngredient>> {
    private final AllIngredientsListener listener;

    public AllIngredientsObserver(AllIngredientsListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        listener.onGetAllIngredientLoading();
    }

    @Override
    public void onSuccess(@NonNull List<MinIngredient> strings) {
        listener.onGetAllIngredientSuccess(strings);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        listener.onGetAllIngredientError(e.getMessage());
    }
}