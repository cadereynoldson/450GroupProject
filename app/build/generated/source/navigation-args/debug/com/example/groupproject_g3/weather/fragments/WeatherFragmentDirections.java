package com.example.groupproject_g3.weather.fragments;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.example.groupproject_g3.R;

public class WeatherFragmentDirections {
  private WeatherFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionNavigationWeatherToNavigationWeatherForecast() {
    return new ActionOnlyNavDirections(R.id.action_navigation_weather_to_navigation_weather_forecast);
  }

  @NonNull
  public static NavDirections actionNavigationWeatherToNavigationWeatherSearch() {
    return new ActionOnlyNavDirections(R.id.action_navigation_weather_to_navigation_weather_search);
  }
}
