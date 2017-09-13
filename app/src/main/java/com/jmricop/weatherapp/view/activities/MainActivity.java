package com.jmricop.weatherapp.view.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jmricop.weatherapp.R;
import com.jmricop.weatherapp.model.Cities;
import com.jmricop.weatherapp.module.MainModule;
import com.jmricop.weatherapp.interactor.MainInteractor;
import com.jmricop.weatherapp.view.fragments.BlankFragment;
import com.jmricop.weatherapp.view.fragments.RecentCitiesFragment;

import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

public class MainActivity extends AppCompatActivity implements MainInteractor.MainView,
        RecentCitiesFragment.OnFragmentInteractionListener, BlankFragment.OnFragmentInteractionListener,
        SearchView.OnQueryTextListener{

    @Inject
    MainInteractor.MainPresenter mainPresenter;

    private ProgressBar progressBar;
    private LinearLayout main_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Inyecta las clases con Dagger. Esto solo lo tenemos aquí por simplicidad.
        ObjectGraph objectGraph = ObjectGraph.create(new MainModule());
        objectGraph.inject(this);

        // Le dice al presenter cuál es su vista
        mainPresenter.setVista(this);

        main_layout = (LinearLayout) findViewById(R.id.main_layout);

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
        progressBar = getLayoutInflater().inflate(R.layout.progress_bar, main_layout).findViewById(R.id.progressBar_cyclic);
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void dismissProgressDialog() {

    }

    @Override
    public void addSearchedCitiesFragment(List<Cities.City> citiesList) {
        // Create fragment and give it an argument specifying the article it should show
        BlankFragment newFragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(BlankFragment.ARG_PARAM1, citiesList.get(0).name);
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.ll_main, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
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


    private void setupSearchView(Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(this);
    }


    private void addRecentCitiesFragment(Bundle savedInstanceState){
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.ll_main) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            RecentCitiesFragment recentCitiesFragment = new RecentCitiesFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().add(R.id.ll_main, recentCitiesFragment).commit();
        }
    }
}

