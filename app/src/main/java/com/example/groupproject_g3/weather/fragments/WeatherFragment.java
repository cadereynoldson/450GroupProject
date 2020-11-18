/**
 * Landing page for Weather App.
 */
package com.example.groupproject_g3.weather.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentPageWeatherBinding;

/**
 * A fragment to display the weather page.
 *
 * @author
 * @version September 2020
 */
public class WeatherFragment extends Fragment {

    private FragmentPageWeatherBinding binding;

    private WeatherViewModel mWeatherModel;

    /** Require empty public constructor for the Fragment. */

    public WeatherFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page_weather, container, false);
    }
}