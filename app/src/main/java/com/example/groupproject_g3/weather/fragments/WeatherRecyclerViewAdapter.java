package com.example.groupproject_g3.weather.fragments;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Recycle view adapter for hours and days item.
 *
 * @author Charles Bryan
 * @version April 2020
 */
public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.WeatherViewHolder> {
    @NonNull
    @Override
    public WeatherRecyclerViewAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherRecyclerViewAdapter.WeatherViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
