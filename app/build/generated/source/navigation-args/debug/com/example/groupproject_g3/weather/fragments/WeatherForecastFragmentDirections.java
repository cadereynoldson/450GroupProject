package com.example.groupproject_g3.weather.fragments;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.example.groupproject_g3.R;

public class WeatherForecastFragmentDirections {
  private WeatherForecastFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionNavigationWeatherForecastToNavigationWeatherSearch() {
    return new ActionOnlyNavDirections(R.id.action_navigation_weather_forecast_to_navigation_weather_search);
  }

  @NonNull
  public static NavDirections actionNavigationWeatherForecastToNavigationWeather() {
    return new ActionOnlyNavDirections(R.id.action_navigation_weather_forecast_to_navigation_weather);
  }
}
