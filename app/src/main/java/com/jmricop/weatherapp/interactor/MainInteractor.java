package com.jmricop.weatherapp.interactor;

import android.content.Context;

import com.jmricop.weatherapp.model.Cities;
import com.jmricop.weatherapp.model.Stations;

public interface MainInteractor {

    interface MainView {
        Context getContext();
        void showAlert(String message);
        void showProgressDialog();
        void dismissProgressDialog();
        void addSearchedCitiesFragment(Cities.City[] citiesList);
        void addFragmentCityDetail(Cities.City city, Stations.Station[] stations);
        void refreshSearchedCities();
        void searchCity(String search);
    }

    interface MainPresenter {
        void setVista(MainView vista);
        void searchCity(String name);
        String[] getRecentSearches();
    }
}
