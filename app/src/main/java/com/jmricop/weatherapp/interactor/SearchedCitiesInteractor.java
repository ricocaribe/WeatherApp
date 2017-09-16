package com.jmricop.weatherapp.interactor;

import android.content.Context;

import com.jmricop.weatherapp.model.Cities;
import com.jmricop.weatherapp.model.Stations;


public interface SearchedCitiesInteractor {

    interface SearchedCitiesView {
        Context getContext();
        void showAlert(String message);
        void showProgressDialog();
        void dismissProgressDialog();
        void showCity(Cities.City city, Stations.Station[] stations);
    }


    interface SearchedCitiesPresenter {
        void setVista(SearchedCitiesInteractor.SearchedCitiesView searchedCitiesView);
        void searchCityWeatherInfo(Cities.City city);
    }
}
