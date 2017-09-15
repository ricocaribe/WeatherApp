package com.jmricop.weatherapp.view.adapter;


import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
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

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;


public class CityDetailStationsAdapter extends RecyclerView.Adapter<CityDetailStationsAdapter.ViewHolder> {

    private Stations.Station[] stations;
    private FragmentActivity fragmentActivity;

    public CityDetailStationsAdapter(Stations.Station[] stations, FragmentActivity fragmentActivity) {
        this.stations = stations;
        this.fragmentActivity = fragmentActivity;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        PieView pieStationTemp;
        PieView pieStationHum;
        TextView tvStationDetailName;
        TextView tvStationDetailTime;
        TextView tvStationClouds;
        TextView tvStationWind;

        ViewHolder(View v) {
            super(v);
            tvStationDetailName = v.findViewById(R.id.tvStationDetailName);
            tvStationDetailTime = v.findViewById(R.id.tvStationDetailTime);
            tvStationClouds = v.findViewById(R.id.tvStationClouds);
            tvStationWind = v.findViewById(R.id.tvStationWind);

            pieStationTemp = v.findViewById(R.id.pieStationTemp);
            pieStationHum = v.findViewById(R.id.pieStationHum);
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

        holder.tvStationDetailTime.setText(sdf.format(date));
        holder.tvStationClouds.setText(stations[position].clouds);
        holder.tvStationWind.setText(String.format("%skm/h", stations[position].windSpeed));

        holder.pieStationTemp.setPercentage(Float.parseFloat(stations[position].temperature));
        holder.pieStationTemp.setPercentageBackgroundColor(getCustomTempBackgroundColour(Integer.parseInt(stations[position].temperature)));
        holder.pieStationTemp.setInnerText(stations[position].temperature);

        PieAngleAnimation pieAnimationTemp = new PieAngleAnimation(holder.pieStationTemp);
        pieAnimationTemp.setDuration(2000);
        holder.pieStationTemp.startAnimation(pieAnimationTemp);

        holder.pieStationHum.setPercentage(stations[position].humidity);
        holder.pieStationHum.setPercentageBackgroundColor(getCustomHumBackgroundColour(stations[position].humidity));
        holder.pieStationHum.setInnerText(String.valueOf(stations[position].humidity));

        PieAngleAnimation pieAnimationHum = new PieAngleAnimation(holder.pieStationHum);
        pieAnimationHum.setDuration(2000);
        holder.pieStationHum.startAnimation(pieAnimationHum);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return stations.length;
    }


    private int getCustomTempBackgroundColour(int temperature){
        if(temperature<=5) return ContextCompat.getColor(fragmentActivity, R.color.color_low_temp03);
        else if (temperature>5 && temperature<=10) return ContextCompat.getColor(fragmentActivity, R.color.color_midle_temp02);
        else if (temperature>10 && temperature<=15) return ContextCompat.getColor(fragmentActivity, R.color.color_midle_temp01);
        else if (temperature>15 && temperature<=20) return ContextCompat.getColor(fragmentActivity, R.color.color_midle_temp01);
        else if (temperature>20 && temperature<=25) return ContextCompat.getColor(fragmentActivity, R.color.color_midle_temp02);
        else if (temperature>25 && temperature<=30) return ContextCompat.getColor(fragmentActivity, R.color.color_midle_temp03);
        else if (temperature>30 && temperature<=35) return ContextCompat.getColor(fragmentActivity, R.color.color_midle_temp01);
        else if (temperature>35 && temperature<=40) return ContextCompat.getColor(fragmentActivity, R.color.color_midle_temp02);
        else return ContextCompat.getColor(fragmentActivity, R.color.color_hight_temp03);
    }

    private int getCustomHumBackgroundColour(int humidity){
        if(humidity<=15) return ContextCompat.getColor(fragmentActivity, R.color.color_hum_01);
        else if (humidity>30 && humidity<=50) return ContextCompat.getColor(fragmentActivity, R.color.color_hum_02);
        else if (humidity>50 && humidity<=70) return ContextCompat.getColor(fragmentActivity, R.color.color_hum_03);
        else if (humidity>70 && humidity<=85) return ContextCompat.getColor(fragmentActivity, R.color.color_hum_04);
        else return ContextCompat.getColor(fragmentActivity, R.color.color_hum_05);

    }
}