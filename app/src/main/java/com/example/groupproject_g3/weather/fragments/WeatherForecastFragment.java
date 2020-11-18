package com.example.groupproject_g3.weather.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentWeatherForecastBinding;

/**
 * A fragment to display the weather forecast.
 *
 * @author
 * @version November 2020
 */
public class WeatherForecastFragment extends Fragment {

    private FragmentWeatherForecastBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_forecast, container, false);
    }
}