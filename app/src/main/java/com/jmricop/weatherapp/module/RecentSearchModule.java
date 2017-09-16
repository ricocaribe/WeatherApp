package com.jmricop.weatherapp.module;

import com.jmricop.weatherapp.interactor.RecentSearchInteractor;
import com.jmricop.weatherapp.presenter.RecentSearchPresenter;
import com.jmricop.weatherapp.view.fragments.RecentSearchFragment;

import dagger.Module;
import dagger.Provides;


@Module(injects = {RecentSearchFragment.class})
public class RecentSearchModule {

    @Provides
    public RecentSearchInteractor.RecentCitiesPresenter provideRecentSearchPresenter(){
        return new RecentSearchPresenter();
    }
}
