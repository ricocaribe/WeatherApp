package com.jmricop.weatherapp.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmricop.weatherapp.R;
import com.jmricop.weatherapp.model.Cities;
import com.jmricop.weatherapp.model.Stations;

public class CityDetailFragment extends Fragment{

    public static final String ARG_CITY = "city";
    public static final String ARG_STATIONS = "stations";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_detail, container, false);
        TextView cityDetailName = view.findViewById(R.id.cityDetailName);
        cityDetailName.setText(((Cities.City) (getArguments().getSerializable(ARG_CITY))).name);
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
}
