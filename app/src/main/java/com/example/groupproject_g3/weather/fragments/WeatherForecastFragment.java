package com.example.groupproject_g3.weather.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentPageWeatherForecastBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherForecastFragment extends Fragment {

    private WeatherBackground background;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page_weather_forecast, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WeatherForecastFragmentArgs args = WeatherForecastFragmentArgs.fromBundle(getArguments());
        FragmentPageWeatherForecastBinding binding = FragmentPageWeatherForecastBinding.bind(getView());
        Picasso.get().load(args.getInformation().getmIcon()).into(binding.imageForecastIcon);
        binding.textForecastDate.setText(args.getInformation().getmDay() + " " + args.getInformation().getmDate());
        binding.textMinimumData.setText(args.getInformation().getmTempMin() + "°F");
        binding.textMaximumData.setText(args.getInformation().getmTempMax() + "°F");
        binding.textForecastDescription.setText(args.getInformation().getmWeather());
        binding.textForecastTime.setText(args.getInformation().getmTime());
        binding.textFeelslikeData.setText(args.getInformation().getmFeelsLike() + "°F");
        binding.textPressureData.setText(args.getInformation().getmPressure() + " hPa");
        binding.textHumidityData.setText(args.getInformation().getmHumidity() + "%");
        binding.textForecastLocation.setText(args.getInformation().getmLocation() + ", " + args.getInformation().getmCountry());
        binding.textRainData.setText(args.getInformation().getmPop());
        binding.textLatitudeData.setText(args.getInformation().getmLat());
        binding.textLongitudeData.setText(args.getInformation().getmLon());
        binding.textSpeedData.setText(args.getInformation().getmSpeed());
        binding.textDirectionData.setText(args.getInformation().getmDirection());
        background = new WeatherBackground(view, args.getInformation().getmWeather(), args.getInformation().getmIcon(), getResources());
    }
}