package com.jmricop.weatherapp.interactor;

import com.jmricop.weatherapp.model.Cities;

import java.util.List;

public interface MainInteractor {

    interface MainView {
        void showAlert();
        void showProgressDialog();
        void dismissProgressDialog();
        void addSearchedCitiesFragment(List<Cities.City> citiesList);
    }

    interface MainPresenter {
        void setVista(MainView vista);
        void searchCity(String name);
    }
}
