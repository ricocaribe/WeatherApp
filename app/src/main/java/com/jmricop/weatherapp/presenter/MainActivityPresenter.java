package com.jmricop.weatherapp.presenter;


import android.util.Log;

import com.google.gson.Gson;
import com.jmricop.weatherapp.api.WeatherRetrofitClient;
import com.jmricop.weatherapp.api.WeatherRetrofitInterface;
import com.jmricop.weatherapp.interactor.MainInteractor;
import com.jmricop.weatherapp.model.Cities;

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

        WeatherRetrofitClient.searchCity().create(WeatherRetrofitInterface.class).searchCity(name,20,0,"en","true","FULL","ilgeonamessample").enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {

                mainView.dismissProgressDialog();

                if(null!=response.body()) {
                    Log.i(getClass().getSimpleName(), "Cities: " + new Gson().toJson(response));
                    //mainView.setSuperherosAdapter(response.body().superheros);
                }

            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                mainView.dismissProgressDialog();
                mainView.showAlert();
                call.cancel();
                t.printStackTrace();
            }
        });
    }

//    @Override
//    public void goDetailActivity(Cities.Superhero superhero) {
//        mainView.goSuperheroDetail(superhero);
//    }
}
