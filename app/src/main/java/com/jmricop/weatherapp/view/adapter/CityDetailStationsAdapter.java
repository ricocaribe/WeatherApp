package com.jmricop.weatherapp.view.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmricop.weatherapp.R;
import com.jmricop.weatherapp.model.Stations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;


public class CityDetailStationsAdapter extends RecyclerView.Adapter<CityDetailStationsAdapter.ViewHolder> {

    private Stations.Station[] stations;
    private int gmtOffset;

    public CityDetailStationsAdapter(Stations.Station[] stations, int gmtOffset) {
        this.stations = stations;
        this.gmtOffset = gmtOffset;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvStationDetailName;
        TextView tvStationDetailTime;
        TextView tvStationClouds;
        TextView tvStationWind;
        CircleProgressView mCircleViewTemp;
        CircleProgressView mCircleViewHum;

        ViewHolder(View v) {
            super(v);
            tvStationDetailName = v.findViewById(R.id.tvStationDetailName);
            tvStationDetailTime = v.findViewById(R.id.tvStationDetailTime);
            tvStationClouds = v.findViewById(R.id.tvStationClouds);
            tvStationWind = v.findViewById(R.id.tvStationWind);
            mCircleViewTemp = v.findViewById(R.id.circleViewTemp);
            mCircleViewHum = v.findViewById(R.id.circleViewHum);
        }
    }


    @Override
    public CityDetailStationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_detail_station, parent, false);
        return new CityDetailStationsAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(CityDetailStationsAdapter.ViewHolder holder, final int position) {

        holder.tvStationDetailName.setText(stations[position].stationName);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;

        try {
            date = sdf.parse(stations[position].datetime);
            sdf = new SimpleDateFormat();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvStationDetailTime.setText(sdf.format(date) + " GMT " + (gmtOffset>=0?("+" + gmtOffset):gmtOffset));
        holder.tvStationClouds.setText(stations[position].clouds);
        holder.tvStationWind.setText(String.format("%s km/h", Integer.parseInt(stations[position].windSpeed)));

        holder.mCircleViewTemp.setTextMode(TextMode.VALUE);
        holder.mCircleViewTemp.setValueAnimated(Float.parseFloat(stations[position].temperature), 3000);

        holder.mCircleViewHum.setValueAnimated(stations[position].humidity, 3000);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return stations.length;
    }

}