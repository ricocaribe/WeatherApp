package com.jmricop.weatherapp.view.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.widget.ProgressBar;

import com.jmricop.weatherapp.R;
import com.jmricop.weatherapp.dagger.MainModule;
import com.jmricop.weatherapp.interactor.MainInteractor;
import com.jmricop.weatherapp.view.fragments.RecentCitiesFragment;

import javax.inject.Inject;

import dagger.ObjectGraph;

public class MainActivity extends AppCompatActivity implements MainInteractor.MainView,
        RecentCitiesFragment.OnFragmentInteractionListener, SearchView.OnQueryTextListener{

    @Inject
    MainInteractor.MainPresenter mainPresenter;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Inyecta las clases con Dagger. Esto solo lo tenemos aquí por simplicidad.
        ObjectGraph objectGraph = ObjectGraph.create(new MainModule());
        objectGraph.inject(this);

        // Le dice al presenter cuál es su vista
        mainPresenter.setVista(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_search, menu);
        setupSearchView(menu);
        return true;
    }

    @Override
    public void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(getResources().getString(R.string.app_name));
        alertDialog.setMessage(getResources().getString(R.string.error_something_wrong));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        mainPresenter.getSuperheros();
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }


    @Override
    public void showProgressDialog() {
//        progressBar.setCancelable(false);
//        progressBar.setMessage(getResources().getString(R.string.tv_checking_superheros));
//        progressBar.disk();
    }


    @Override
    public void dismissProgressDialog() {
//        if(null!=pdChecking && pdChecking.isShowing()) pdChecking.dismiss();
    }

    private void setupSearchView(Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(this);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override public boolean onQueryTextSubmit(String query) {
        mainPresenter.searchCity(query);
        return true;
    }

    @Override public boolean onQueryTextChange(String newText) {
        return true;
    }
}

