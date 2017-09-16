package com.jmricop.weatherapp.presenter;

import com.jmricop.weatherapp.interactor.RecentSearchInteractor;

import com.jmricop.weatherapp.view.fragments.RecentSearchFragment;

public class RecentSearchPresenter implements RecentSearchInteractor.RecentCitiesPresenter {

    private RecentSearchFragment recentSearchFragment;

    @Override
    public void setVista(RecentSearchFragment recentSearchFragment) {
        this.recentSearchFragment = recentSearchFragment;
    }

    @Override
    public void searchCityAgain(String search) {
        recentSearchFragment.searchCity(search);
    }
}
