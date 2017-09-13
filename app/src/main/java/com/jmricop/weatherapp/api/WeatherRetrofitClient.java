package com.jmricop.weatherapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRetrofitClient {

    public static Retrofit searchCity() {

        return new Retrofit.Builder()
                .baseUrl(WeatherRetrofitInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
