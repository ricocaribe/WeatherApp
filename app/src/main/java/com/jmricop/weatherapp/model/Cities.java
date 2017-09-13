package com.jmricop.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Cities implements Serializable {

    @SerializedName("geonames") public List<City> citiesList;

    public class City implements Serializable {

        @SerializedName("name") public String name;
    }
}
