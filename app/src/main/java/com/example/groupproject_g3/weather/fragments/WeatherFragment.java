/**
 * Landing page for Weather App.
 */
package com.example.groupproject_g3.weather.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentPageWeatherBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {

    private double lat = 47.2529;

    private double lon = -122.4443;

    private FragmentPageWeatherBinding binding;

    private WeatherCurrentViewModel currentModel;

    private WeatherForecastViewModel forecastModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        UserInfoViewModel model = provider.get(UserInfoViewModel.class);
        currentModel = new ViewModelProvider(getActivity()).get(WeatherCurrentViewModel.class);
        currentModel.connectGet(model.getJwt(), lat, lon); //TODO: Update hardcoded lat and long.
        forecastModel = new ViewModelProvider(getActivity()).get(WeatherForecastViewModel.class);
        forecastModel.connectGet(model.getJwt(), lat, lon);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPageWeatherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentModel.addCurrentWeatherObserver(getViewLifecycleOwner(), currentInfo -> {
            binding.containerWeatherForecast.textWeatherDate.setText(currentInfo.getDate());
            binding.containerWeatherForecast.textWeatherTemperature.setText(currentInfo.getTemperature() + "°F"); //TODO: Update for celsius
            binding.containerWeatherForecast.textWeatherDescription.setText(currentInfo.getWeather());
            binding.containerWeatherForecast.textWeatherTime.setText(currentInfo.getTime());
        });
        forecastModel.addForecastListObserver(getViewLifecycleOwner(), information -> {
            if (!information.isEmpty()){
                binding.forecastInfo.setAdapter(new WeatherForecastRecyclerViewAdapter(information));
                Log.e("SET ADAPTER", "Hello!");
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
}