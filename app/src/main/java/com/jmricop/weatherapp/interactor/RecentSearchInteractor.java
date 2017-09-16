package com.jmricop.weatherapp.interactor;

import android.content.Context;


public interface RecentSearchInteractor {

    interface RecentCitiesView {
        void showCity(String search);
    }


    interface RecentCitiesPresenter {
        void setVista(RecentSearchInteractor.RecentCitiesView recentCitiesView);
        void searchCity(String search);
    }
}
