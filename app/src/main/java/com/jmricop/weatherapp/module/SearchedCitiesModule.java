package com.jmricop.weatherapp.module;

import com.jmricop.weatherapp.interactor.SearchedCitiesInteractor;
import com.jmricop.weatherapp.presenter.SearchedCitiesPresenter;
import com.jmricop.weatherapp.view.activities.MainActivity;
import com.jmricop.weatherapp.view.fragments.SearchedCitiesFragment;

import dagger.Module;
import dagger.Provides;


@Module(injects = {SearchedCitiesFragment.class})
public class SearchedCitiesModule {

    @Provides
    public SearchedCitiesInteractor.SearchedCitiesPresenter provideSearchedCitiesPresenter(){
        return new SearchedCitiesPresenter();
    }
}
