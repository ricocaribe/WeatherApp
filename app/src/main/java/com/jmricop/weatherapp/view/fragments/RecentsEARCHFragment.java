package com.jmricop.weatherapp.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmricop.weatherapp.R;
import com.jmricop.weatherapp.interactor.MainInteractor;
import com.jmricop.weatherapp.interactor.RecentSearchInteractor;
import com.jmricop.weatherapp.module.RecentSearchModule;
import com.jmricop.weatherapp.view.adapter.RecentSearchAdapter;

import javax.inject.Inject;

import dagger.ObjectGraph;

public class RecentSearchFragment extends Fragment implements RecentSearchInteractor.RecentCitiesView {

    @Inject
    RecentSearchInteractor.RecentCitiesPresenter recentCitiesPresenter;

    public static final String ARG_RECENTS = "recentSearches";
    private String[] recentSearches;
    private MainInteractor.MainView mainView;
    private RecentSearchAdapter recentSearchAdapter;

    public static RecentSearchFragment newInstance(String[] recentSearches) {
        RecentSearchFragment fragment = new RecentSearchFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECENTS, recentSearches);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ObjectGraph objectGraph = ObjectGraph.create(new RecentSearchModule());
        objectGraph.inject(this);

        recentCitiesPresenter.setVista(this);

        if (getArguments() != null) {
            recentSearches = (String[]) getArguments().getSerializable(ARG_RECENTS);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recent_searches, container, false);
        RecyclerView rvSearchedCities = view.findViewById(R.id.rvRecentSearches);
        rvSearchedCities.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvSearchedCities.setLayoutManager(layoutManager);

        recentSearchAdapter = new RecentSearchAdapter(recentCitiesPresenter);
        recentSearchAdapter.setSearchedCities(recentSearches);

        rvSearchedCities.setAdapter(recentSearchAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mainView.refreshSearchedCities();
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
    public void showCity(String search) {
        mainView.searchCity(search);
    }

    public void refreshCities(String[] cities){
        recentSearchAdapter.setSearchedCities(cities);
        recentSearchAdapter.notifyDataSetChanged();
    }
}
