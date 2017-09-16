package com.jmricop.weatherapp.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmricop.weatherapp.R;
import com.jmricop.weatherapp.interactor.SearchedCitiesInteractor;
import com.jmricop.weatherapp.model.Cities;
import com.jmricop.weatherapp.model.Stations;
import com.jmricop.weatherapp.module.SearchedCitiesModule;
import com.jmricop.weatherapp.view.adapter.SearchedCitiesAdapter;

import javax.inject.Inject;

import dagger.ObjectGraph;


public class SearchedCitiesFragment extends Fragment implements SearchedCitiesInteractor.SearchedCitiesView{

    @Inject
    SearchedCitiesInteractor.SearchedCitiesPresenter searchedCitiesPresenter;

    public static final String ARG_CITIES = "cities";
    private Cities.City[] citiesParam;
    private OnFragmentInteractionListener mListener;


    public static SearchedCitiesFragment newInstance(Cities.City[] cities) {
        SearchedCitiesFragment fragment = new SearchedCitiesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITIES, cities);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ObjectGraph objectGraph = ObjectGraph.create(new SearchedCitiesModule());
        objectGraph.inject(this);

        searchedCitiesPresenter.setVista(this);

        if (getArguments() != null) {
            citiesParam = (Cities.City[]) getArguments().getSerializable(ARG_CITIES);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_searched_cities, container, false);
        RecyclerView rvSearchedCities = view.findViewById(R.id.rvSearchedCities);
        rvSearchedCities.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rvSearchedCities.setLayoutManager(layoutManager);

        SearchedCitiesAdapter searchedCitiesAdapter = new SearchedCitiesAdapter(searchedCitiesPresenter, mListener);
        searchedCitiesAdapter.setSearchedCities(citiesParam);

        rvSearchedCities.setAdapter(searchedCitiesAdapter);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void searchCityWeatherInfo(Cities.City city, Stations.Station[] stations){
        mListener.addFragmentCityDetail(city, stations);
    }

    @Override
    public void showAlert(String message) {
        mListener.showAlert(message);
    }

    @Override
    public void showProgressDialog() {
        mListener.showProgressDialog();
    }

    @Override
    public void dismissProgressDialog() {
        mListener.dismissProgressDialog();
    }

}
