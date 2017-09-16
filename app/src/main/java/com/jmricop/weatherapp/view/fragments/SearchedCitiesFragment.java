package com.jmricop.weatherapp.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmricop.weatherapp.R;
import com.jmricop.weatherapp.interactor.MainInteractor;
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
    private MainInteractor.MainView mainView;


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

        if(citiesParam.length>0){

            RecyclerView rvSearchedCities = view.findViewById(R.id.rvSearchedCities);
            rvSearchedCities.setVisibility(View.VISIBLE);
            rvSearchedCities.setHasFixedSize(true);

            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            rvSearchedCities.setLayoutManager(layoutManager);

            SearchedCitiesAdapter searchedCitiesAdapter = new SearchedCitiesAdapter(searchedCitiesPresenter);
            searchedCitiesAdapter.setSearchedCities(citiesParam);

            rvSearchedCities.setAdapter(searchedCitiesAdapter);

        }
        else {
            TextView tvNoData = view.findViewById(R.id.tvNoResults);
            tvNoData.setVisibility(View.VISIBLE);
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainInteractor.MainView) {
            mainView = (MainInteractor.MainView) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mainView = null;
    }


    @Override
    public void showCity(Cities.City city, Stations.Station[] stations){
        mainView.addFragmentCityDetail(city, stations);
    }


    @Override
    public void showAlert(String message) {
        mainView.showAlert(message);
    }


    @Override
    public void showProgressDialog() {
        mainView.showProgressDialog();
    }


    @Override
    public void dismissProgressDialog() {
        mainView.dismissProgressDialog();
    }

}
