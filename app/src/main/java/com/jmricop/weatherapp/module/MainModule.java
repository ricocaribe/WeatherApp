package com.jmricop.weatherapp.module;

import com.jmricop.weatherapp.interactor.MainInteractor;
import com.jmricop.weatherapp.presenter.MainActivityPresenter;
import com.jmricop.weatherapp.view.activities.MainActivity;

import dagger.Module;
import dagger.Provides;


@Module(injects = {MainActivity.class})
public class MainModule {

    @Provides
    public MainInteractor.MainPresenter provideMainActivityPresenter(){
        return new MainActivityPresenter();
    }
}
