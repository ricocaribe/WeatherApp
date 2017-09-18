package com.jmricop.weatherapp.view.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.jmricop.weatherapp.R;
import com.jmricop.weatherapp.interactor.RecentSearchInteractor;
import com.jmricop.weatherapp.interactor.SearchedCitiesInteractor;
import com.jmricop.weatherapp.model.Cities;
import com.jmricop.weatherapp.model.Stations;
import com.jmricop.weatherapp.module.MainModule;
import com.jmricop.weatherapp.interactor.MainInteractor;
import com.jmricop.weatherapp.view.fragments.CityDetailFragment;
import com.jmricop.weatherapp.view.fragments.SearchedCitiesFragment;
import com.jmricop.weatherapp.view.fragments.RecentSearchFragment;

import javax.inject.Inject;

import dagger.ObjectGraph;

public class MainActivity extends AppCompatActivity implements MainInteractor.MainView,
        RecentSearchInteractor.RecentCitiesView, SearchedCitiesInteractor.SearchedCitiesView,
        SearchView.OnQueryTextListener{

    @Inject
    MainInteractor.MainPresenter mainPresenter;

    private SearchView searchView;
    private RecentSearchFragment recentCitiesFragment;
    private AlertDialog progressDialog;
    private LinearLayout main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ObjectGraph objectGraph = ObjectGraph.create(new MainModule());
        objectGraph.inject(this);

        main_layout = (LinearLayout) findViewById(R.id.main_layout);
        mainPresenter.setVista(this);

        addRecentCitiesFragment(savedInstanceState);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_search, menu);
        setupSearchView(menu);
        return true;
    }


    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void showAlert(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(getResources().getString(R.string.app_name));
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }


    @Override
    public void showProgressDialog() {
        progressDialog = new AlertDialog.Builder(MainActivity.this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.progress_bar, null);
        progressDialog.setCancelable(false);
        progressDialog.setView(dialogView);
        progressDialog.show();
    }


    @Override
    public void dismissProgressDialog() {
        progressDialog.cancel();
    }

    @Override
    public void showCity(Cities.City city, Stations.Station[] stations) {
        addFragmentCityDetail(city, stations);
    }


    @Override
    public void addSearchedCitiesFragment(Cities.City[] citiesList) {

        SearchedCitiesFragment searchedCitiesFragment = SearchedCitiesFragment.newInstance(citiesList);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_layout, searchedCitiesFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        mainPresenter.searchCity(query);
        searchView.clearFocus();
        return true;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }


    private void setupSearchView(Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(this);
    }


    private void addRecentCitiesFragment(Bundle savedInstanceState){

        if (main_layout != null) {

            if (savedInstanceState != null) return;

            recentCitiesFragment = RecentSearchFragment.newInstance(mainPresenter.getRecentSearches());

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_layout, recentCitiesFragment);
            transaction.commit();
        }
    }



    @Override
    public void addFragmentCityDetail(Cities.City city, Stations.Station[] stations) {
        CityDetailFragment cityDetailFragment = CityDetailFragment.newInstance(city, stations);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_layout, cityDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void searchCity(String search) {
        onQueryTextSubmit(search);
    }

    @Override
    public void refreshSearchedCities() {
        recentCitiesFragment.refreshCities(mainPresenter.getRecentSearches());
    }

    @Override
    public void showCity(String city) {
        searchCity(city);
    }

    @Override
    public void clearFocus() {
        searchView.clearFocus();
        main_layout.requestFocus();
    }
}

