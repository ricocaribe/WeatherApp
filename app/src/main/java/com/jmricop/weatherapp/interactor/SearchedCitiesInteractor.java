package com.jmricop.weatherapp.interactor;

import android.content.Context;

import com.jmricop.weatherapp.model.Cities;
import com.jmricop.weatherapp.view.fragments.SearchedCitiesFragment;


public interface SearchedCitiesInteractor {

    interface SearchedCitiesView {
        Context getContext();
        void showAlert(String message);
        void showProgressDialog();
        void dismissProgressDialog();
    }


    interface SearchedCitiesPresenter {
        void setVista(SearchedCitiesFragment searchedCitiesFragment);
        void searchCityWeatherInfo(Cities.City city, double north, double south, double east, double west);
    }
}
