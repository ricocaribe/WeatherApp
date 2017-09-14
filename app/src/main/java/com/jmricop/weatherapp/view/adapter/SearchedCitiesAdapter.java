package com.jmricop.weatherapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmricop.weatherapp.R;
import com.jmricop.weatherapp.interactor.SearchedCitiesInteractor;
import com.jmricop.weatherapp.model.Cities;
import com.jmricop.weatherapp.view.fragments.SearchedCitiesFragment;


public class SearchedCitiesAdapter extends RecyclerView.Adapter<SearchedCitiesAdapter.ViewHolder> {

    private Cities.City[] cities;
    private SearchedCitiesInteractor.SearchedCitiesPresenter searchedCitiesPresenter;
    private SearchedCitiesFragment.OnFragmentInteractionListener mListener;

    public SearchedCitiesAdapter(SearchedCitiesInteractor.SearchedCitiesPresenter searchedCitiesPresenter,
                                 SearchedCitiesFragment.OnFragmentInteractionListener mListener) {
        this.searchedCitiesPresenter = searchedCitiesPresenter;
        this.mListener = mListener;
    }


    public void setSearchedCities(Cities.City[] cities) {
        this.cities = cities;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView citiyInfo;
        ImageView cityBg;

        ViewHolder(View v) {
            super(v);
            citiyInfo = v.findViewById(R.id.cityInfo);
            cityBg = v.findViewById(R.id.cityBg);
        }
    }


    @Override
    public SearchedCitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_searched_city, parent, false);
        return new SearchedCitiesAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(SearchedCitiesAdapter.ViewHolder holder, final int position) {

        swicthBackground(holder, position);

        holder.citiyInfo.setText(cities[position].name + ", " + cities[position].countryCode);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cities[position].bbox!=null){
                    searchedCitiesPresenter.searchCityWeatherInfo(cities[position],
                            cities[position].bbox.north,
                            cities[position].bbox.south,
                            cities[position].bbox.east,
                            cities[position].bbox.west);
                }
                else mListener.showAlert(mListener.getContext().getResources().getString(R.string.error_something_wrong));
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return cities.length;
    }


    private void swicthBackground(SearchedCitiesAdapter.ViewHolder holder, int position){
        switch (cities[position].continentCode){
            case "EU":
                holder.cityBg.setBackgroundResource(R.drawable.ic_eu);
                break;
            case "SA":
                holder.cityBg.setBackgroundResource(R.drawable.ic_sa);
                break;
            case "NA":
                holder.cityBg.setBackgroundResource(R.drawable.ic_na);
                break;
            case "AF":
                holder.cityBg.setBackgroundResource(R.drawable.ic_af);
                break;
            case "AS":
                holder.cityBg.setBackgroundResource(R.drawable.ic_as);
                break;
            case "OC":
                holder.cityBg.setBackgroundResource(R.drawable.ic_oc);
                break;
            default:
                break;
        }
    }
}