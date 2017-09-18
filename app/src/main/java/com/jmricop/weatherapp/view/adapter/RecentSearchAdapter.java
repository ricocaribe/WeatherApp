package com.jmricop.weatherapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmricop.weatherapp.R;
import com.jmricop.weatherapp.interactor.RecentSearchInteractor;


public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.ViewHolder> {

    private String[] recentSearches;
    private RecentSearchInteractor.RecentCitiesPresenter recentCitiesPresenter;

    public RecentSearchAdapter(RecentSearchInteractor.RecentCitiesPresenter recentCitiesPresenter) {
        this.recentCitiesPresenter = recentCitiesPresenter;
    }


    public void setSearchedCities(String[] recentSearches) {
        this.recentSearches = recentSearches;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView recentSearch;

        ViewHolder(View v) {
            super(v);
            recentSearch = v.findViewById(R.id.tvRecentSearch);
        }
    }


    @Override
    public RecentSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_search, parent, false);
        return new RecentSearchAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(RecentSearchAdapter.ViewHolder holder, final int position) {

        holder.recentSearch.setText(recentSearches[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recentCitiesPresenter.searchCity(recentSearches[position]);
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return recentSearches.length;
    }
}