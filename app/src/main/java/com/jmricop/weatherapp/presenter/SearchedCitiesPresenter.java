package com.jmricop.weatherapp.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.jmricop.weatherapp.R;
import com.jmricop.weatherapp.api.WeatherRetrofitClient;
import com.jmricop.weatherapp.api.WeatherRetrofitInterface;
import com.jmricop.weatherapp.interactor.SearchedCitiesInteractor;
import com.jmricop.weatherapp.model.Cities;
import com.jmricop.weatherapp.model.Stations;
import com.jmricop.weatherapp.utils.DefaultParams;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchedCitiesPresenter implements SearchedCitiesInteractor.SearchedCitiesPresenter {

    private SearchedCitiesInteractor.SearchedCitiesView searchedCitiesView;


    @Override
    public void setVista(SearchedCitiesInteractor.SearchedCitiesView searchedCitiesView) {
        this.searchedCitiesView = searchedCitiesView;
    }


    @Override
    public void searchCityWeatherInfo(final Cities.City city){

        searchedCitiesView.showProgressDialog();

        if(city.bbox!=null){
            WeatherRetrofitClient.searchCityWeatherInfo().create(WeatherRetrofitInterface.class).searchCityWeatherInfo(
                    city.bbox.north, city.bbox.south, city.bbox.east, city.bbox.west, DefaultParams.SEARCH_CITY_DF_USERNAME).enqueue(new Callback<Stations>() {
                @Override
                public void onResponse(Call<Stations> call, Response<Stations> response) {

                    searchedCitiesView.dismissProgressDialog();

                    if(null!=response.body() && response.body().weatherObservations.length>0) {
                        Log.i(getClass().getSimpleName(), "Stations: " + new Gson().toJson(response));
                        searchedCitiesView.showCity(city, response.body().weatherObservations);
                    }
                    else searchedCitiesView.showAlert(searchedCitiesView.getContext()
                            .getResources().getString(R.string.error_no_weather_details));

                }

                @Override
                public void onFailure(Call<Stations> call, Throwable t) {
                    searchedCitiesView.dismissProgressDialog();
                    searchedCitiesView.showAlert(searchedCitiesView.getContext().
                            getResources().getString(R.string.error_something_wrong));
                    call.cancel();
                    t.printStackTrace();
                }
            });
        }
        else {
            searchedCitiesView.dismissProgressDialog();
            searchedCitiesView.showAlert(searchedCitiesView.getContext()
                    .getResources().getString(R.string.error_no_weather_details));
        }

    }
}
