package com.soha.foodplanner.data.remote.common;

public interface Success<T> {
    void onSuccess(T data);
}
