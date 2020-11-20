/**
 * A weather search fragment.
 */
package com.example.groupproject_g3.weather.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentPageWeatherSearchBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherSearchFragment extends Fragment {

    private FragmentPageWeatherSearchBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.searchWeather.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherSearchFragmentDirections.actionNavigationWeatherSearchToNavigationWeather()));
    }
}