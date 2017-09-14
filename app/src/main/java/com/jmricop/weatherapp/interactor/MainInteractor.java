package com.jmricop.weatherapp.interactor;

import android.content.Context;

import com.jmricop.weatherapp.model.Cities;

public interface MainInteractor {

    interface MainView {
        Context getContext();
        void showAlert(String message);
        void showProgressDialog();
        void dismissProgressDialog();
        void addSearchedCitiesFragment(Cities.City[] citiesList);
    }

    interface MainPresenter {
        void setVista(MainView vista);
        void searchCity(String name);
    }
}
