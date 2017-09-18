package com.jmricop.weatherapp.presenter;


import com.jmricop.weatherapp.R;
import com.jmricop.weatherapp.api.WeatherRetrofitClient;
import com.jmricop.weatherapp.api.WeatherRetrofitInterface;
import com.jmricop.weatherapp.interactor.MainInteractor;
import com.jmricop.weatherapp.model.Cities;
import com.jmricop.weatherapp.utils.DefaultParams;
import com.jmricop.weatherapp.utils.RecentSearchesDB;

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

        storeCity(name);

        mainView.showProgressDialog();

        WeatherRetrofitClient.searchCity().create(WeatherRetrofitInterface.class).searchCity(name,
                DefaultParams.SEARCH_CITY_DF_MAXROWS, DefaultParams.SEARCH_CITY_DF_STARTROW,
                DefaultParams.SEARCH_CITY_DF_LANG, DefaultParams.SEARCH_CITY_DF_NAMEREQUIRED,
                DefaultParams.SEARCH_CITY_DF_STYLE, DefaultParams.SEARCH_CITY_DF_USERNAME).enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {

                if(null!=response.body()) mainView.addSearchedCitiesFragment(response.body().citiesList);

                mainView.dismissProgressDialog();

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

    @Override
    public String[] getRecentSearches() {
        String[] recentSearches;
        RecentSearchesDB sqliteHelper = new RecentSearchesDB(mainView.getContext());
        recentSearches = sqliteHelper.getRecentSearches();

        return recentSearches;
    }


    private void storeCity(String cityname){
        RecentSearchesDB sqliteHelper = new RecentSearchesDB(mainView.getContext());
        sqliteHelper.insertSearch(cityname);
        sqliteHelper.close();

    }
}
