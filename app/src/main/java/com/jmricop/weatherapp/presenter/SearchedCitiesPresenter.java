package com.jmricop.weatherapp.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.jmricop.weatherapp.R;
import com.jmricop.weatherapp.api.WeatherRetrofitClient;
import com.jmricop.weatherapp.api.WeatherRetrofitInterface;
import com.jmricop.weatherapp.interactor.SearchedCitiesInteractor;
import com.jmricop.weatherapp.model.Cities;
import com.jmricop.weatherapp.model.Stations;
import com.jmricop.weatherapp.utils.Constants;
import com.jmricop.weatherapp.view.fragments.SearchedCitiesFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchedCitiesPresenter implements SearchedCitiesInteractor.SearchedCitiesPresenter {

    private SearchedCitiesFragment searchedCitiesFragment;

    @Override
    public void setVista(SearchedCitiesFragment searchedCitiesFragment) {
        this.searchedCitiesFragment = searchedCitiesFragment;
    }


    @Override
    public void searchCityWeatherInfo(final Cities.City city, double north, double south, double east, double west){

        searchedCitiesFragment.showProgressDialog();

        WeatherRetrofitClient.searchCityWeatherInfo().create(WeatherRetrofitInterface.class).searchCityWeatherInfo(
                north, south, east, west, Constants.SEARCH_CITY_DF_USERNAME).enqueue(new Callback<Stations>() {
            @Override
            public void onResponse(Call<Stations> call, Response<Stations> response) {

                searchedCitiesFragment.dismissProgressDialog();

                if(null!=response.body() && response.body().weatherObservations.length>0) {
                    Log.i(getClass().getSimpleName(), "Stations: " + new Gson().toJson(response));
                    searchedCitiesFragment.searchCityWeatherInfo(city, response.body().weatherObservations);
                }
                else searchedCitiesFragment.showAlert(searchedCitiesFragment.getActivity()
                        .getResources().getString(R.string.error_no_weather_details));

            }

            @Override
            public void onFailure(Call<Stations> call, Throwable t) {
                searchedCitiesFragment.dismissProgressDialog();
                searchedCitiesFragment.showAlert(searchedCitiesFragment.getContext().
                        getResources().getString(R.string.error_something_wrong));
                call.cancel();
                t.printStackTrace();
            }
        });
    }
}
