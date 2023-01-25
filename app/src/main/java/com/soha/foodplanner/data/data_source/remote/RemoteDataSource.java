package com.soha.foodplanner.data.data_source.remote;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

public interface RemoteDataSource {
    List<Disposable> disposables = new ArrayList<>();

    default void closeAll() {
        disposables.forEach(Disposable::dispose);
    }
}

