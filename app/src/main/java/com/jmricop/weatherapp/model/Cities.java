package com.jmricop.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cities implements Serializable {

    @SerializedName("geonames") public City[] citiesList;

    public class City implements Serializable {

        @SerializedName("name") public String name;

        @SerializedName("continentCode") public String continentCode;

        @SerializedName("countryName") public String countryName;

        @SerializedName("countryCode") public String countryCode;

        @SerializedName("lng") public double lng;

        @SerializedName("lat") public double lat;

        @SerializedName("bbox") public Bbox bbox;

        public class Bbox implements Serializable {

            @SerializedName("east") public double east;

            @SerializedName("south") public double south;

            @SerializedName("north") public double north;

            @SerializedName("west") public double west;

        }
    }


}
