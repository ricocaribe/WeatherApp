package com.jmricop.weatherapp.view.fragments;

import android.content.Context;

import com.jmricop.weatherapp.model.Cities;
import com.jmricop.weatherapp.model.Stations;

public interface OnFragmentInteractionListener {
    Context getContext();
    void showAlert(String message);
    void showProgressDialog();
    void dismissProgressDialog();
    void addFragmentCityDetail(Cities.City city, Stations.Station[] stations);
    void searchCityAgain(String search);
    void refreshSearchedCities();
}
