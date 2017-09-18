package com.jmricop.weatherapp.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jmricop.weatherapp.R;
import com.jmricop.weatherapp.model.Cities;
import com.jmricop.weatherapp.model.Stations;
import com.jmricop.weatherapp.view.adapter.CityDetailStationsAdapter;


public class CityDetailFragment extends Fragment implements OnMapReadyCallback{

    public static final String ARG_CITY = "city";
    public static final String ARG_STATIONS = "stations";
    private Stations.Station[] stations;
    private Cities.City city;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        city = ((Cities.City) (getArguments().getSerializable(ARG_CITY)));
        stations = ((Stations.Station[]) (getArguments().getSerializable(ARG_STATIONS)));

        View view = inflater.inflate(R.layout.fragment_city_detail, container, false);

        TextView cityDetailName = view.findViewById(R.id.tvCityDetailName);
        cityDetailName.setText((city!=null&&city.name!=null)?city.name:"");

        TextView CityDetailCountry = view.findViewById(R.id.tvCityDetailCountry);
        CityDetailCountry.setText((city!=null&&city.countryName!=null)?city.countryName:"");

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.stationsMap);
        mapFragment.getMapAsync(CityDetailFragment.this);

        RecyclerView rvSearchedCities = view.findViewById(R.id.rvCityDetailStations);
        rvSearchedCities.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvSearchedCities.setLayoutManager(layoutManager);

        CityDetailStationsAdapter cityDetailStationsAdapter = new CityDetailStationsAdapter(stations, city.timezone.gmtOffset);

        rvSearchedCities.setAdapter(cityDetailStationsAdapter);

        return view;
    }


    public static CityDetailFragment newInstance(Cities.City city, Stations.Station[] stations) {
        CityDetailFragment fragment = new CityDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        args.putSerializable(ARG_STATIONS, stations);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onMapReady(GoogleMap stationsMap) {

        stationsMap.getUiSettings().setMapToolbarEnabled(false);

        LatLng center = new LatLng(city.lat, city.lng);

        stationsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 8));

        for (Stations.Station station : stations) {
            String customSnippet = "Temperature: " + station.temperature + "Cº - " + "Humidity: " + String.valueOf(station.humidity) + "%";

            stationsMap.addMarker(new MarkerOptions()
                    .position(new LatLng(station.lat, station.lng))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                    .title(station.stationName)
                    .snippet(customSnippet));
        }
    }
}
