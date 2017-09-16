package com.jmricop.weatherapp.presenter;

import com.jmricop.weatherapp.interactor.RecentSearchInteractor;


public class RecentSearchPresenter implements RecentSearchInteractor.RecentCitiesPresenter {

    private RecentSearchInteractor.RecentCitiesView recentCitiesView;

    @Override
    public void setVista(RecentSearchInteractor.RecentCitiesView recentCitiesView) {
        this.recentCitiesView = recentCitiesView;
    }

    @Override
    public void searchCity(String search) {
        recentCitiesView.showCity(search);
    }
}
