package com.jmricop.weatherapp.presenter;


import android.util.Log;

import com.google.gson.Gson;
import com.jmricop.weatherapp.R;
import com.jmricop.weatherapp.api.WeatherRetrofitClient;
import com.jmricop.weatherapp.api.WeatherRetrofitInterface;
import com.jmricop.weatherapp.interactor.MainInteractor;
import com.jmricop.weatherapp.model.Cities;
import com.jmricop.weatherapp.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter implements MainInteractor.MainPresenter {

    private MainInteractor.MainView mainView;

    @Override
    public void setVista(MainInteractor.MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void searchCity(String name){

        mainView.showProgressDialog();

        WeatherRetrofitClient.searchCity().create(WeatherRetrofitInterface.class).searchCity(name,
                Constants.SEARCH_CITY_DF_MAXROWS, Constants.SEARCH_CITY_DF_STARTROW,
                Constants.SEARCH_CITY_DF_LANG, Constants.SEARCH_CITY_DF_NAMEREQUIRED,
                Constants.SEARCH_CITY_DF_STYLE, Constants.SEARCH_CITY_DF_USERNAME).enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {

                mainView.dismissProgressDialog();

                if(null!=response.body()) {
                    Log.i(getClass().getSimpleName(), "Cities: " + new Gson().toJson(response));
                    mainView.addSearchedCitiesFragment(response.body().citiesList);
                }

            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                mainView.dismissProgressDialog();
                mainView.showAlert(mainView.getContext().getResources().getString(R.string.error_something_wrong));
                call.cancel();
                t.printStackTrace();
            }
        });
    }
}
