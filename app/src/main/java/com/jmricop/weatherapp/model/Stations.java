package com.jmricop.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Stations implements Serializable {

    @SerializedName("weatherObservations") public Station[] weatherObservations;

    public class Station implements Serializable {

        @SerializedName("stationName") public String stationName;

        @SerializedName("lng") public double lng;

        @SerializedName("lat") public double lat;

        @SerializedName("temperature") public String temperature;

        @SerializedName("humidity") public int humidity;

        @SerializedName("clouds") public String clouds;

        @SerializedName("datetime") public String datetime;

        @SerializedName("windSpeed") public String windSpeed;

    }
}
