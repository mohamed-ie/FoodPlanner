package com.soha.foodplanner.ui.common.presenter.store;

import com.soha.foodplanner.ui.common.presenter.Presenter;

import java.util.HashMap;
import java.util.Map;

public class PresenterStoreImpl implements PresenterStore {
    private static PresenterStoreImpl instance;
    private final Map<String, Presenter> presenterHashMap;

    private PresenterStoreImpl() {
        presenterHashMap = new HashMap<>();
    }

    public static PresenterStoreImpl getInstance() {
        if (instance == null)
            instance = new PresenterStoreImpl();
        return instance;
    }

    @Override
    public void remove(String key) {
        presenterHashMap.remove(key);
    }

    @Override
    public Presenter get(String key) {
        return presenterHashMap.get(key);
    }

    @Override
    public void store(String key, Presenter presenter) {
        presenterHashMap.put(key, presenter);
    }

}
