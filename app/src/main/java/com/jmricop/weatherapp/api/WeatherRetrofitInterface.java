package com.jmricop.weatherapp.api;

import com.jmricop.weatherapp.model.Cities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherRetrofitInterface {

    //http://api.geonames.org/searchJSON?q=Madrid&maxRows=20&startRow=0&lang=en&isNameRequired=true&style=FULL&username=ilgeonamessample

    String BASE_URL = "http://api.geonames.org/";
    @GET("/searchJSON")
    Call<Cities> searchCity(
            @Query(value = "q")String query,
            @Query(value = "maxRows") int maxRows,
            @Query(value = "startRow") int startRow,
            @Query(value = "lang") String lang,
            @Query(value = "isNameRequired") String isNameRequired,
            @Query(value = "style") String style,
            @Query(value = "username") String username);
}
