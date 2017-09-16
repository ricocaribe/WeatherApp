package com.jmricop.weatherapp.interactor;

import android.content.Context;

import com.jmricop.weatherapp.view.fragments.RecentSearchFragment;


public interface RecentSearchInteractor {

    interface RecentCitiesView {
        Context getContext();
        void searchCity(String search);
    }


    interface RecentCitiesPresenter {
        void setVista(RecentSearchFragment recentSearchFragment);
        void searchCityAgain(String search);
    }
}
