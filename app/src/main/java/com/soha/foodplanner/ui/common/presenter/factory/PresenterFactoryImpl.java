package com.soha.foodplanner.ui.common.presenter.factory;


import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.ui.common.presenter.Presenter;
import com.soha.foodplanner.ui.common.presenter.store.PresenterStore;
import com.soha.foodplanner.ui.login.presenter.LoginPresenter;

public class PresenterFactoryImpl<P extends Presenter> implements PresenterFactory<P> {
    private final PresenterStore presenterStore;
    private static PresenterFactory instance;

    PresenterFactoryImpl(PresenterStore presenterStore) {
        this.presenterStore = presenterStore;
    }
    @SuppressWarnings("unchecked")
    public static <P extends Presenter> PresenterFactory<P> getInstance(PresenterStore presenterStore) {
        if(instance==null)
            instance= new PresenterFactoryImpl<P>(presenterStore);
        return instance;
    }

    @Override
    public P create(String key, Factory<P> factory) {
        Presenter presenter = presenterStore.get(key);
        if (presenter == null) {
            switch (key) {
                case LoginPresenter.TAG:
                    presenter = factory.create();
                    break;
            }
            presenterStore.store(key, presenter);
        }
        return (P) presenter;
    }

    @Override
    public void onDestroy(String key, boolean isChangingConfigurations) {
        if (!isChangingConfigurations)
            return;

        Presenter presenter = presenterStore.get(key);
        presenter.destroy();
        presenterStore.remove(key);

    }
}