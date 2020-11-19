package com.example.groupproject_g3.weather.fragments;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.example.groupproject_g3.R;

public class WeatherSearchFragmentDirections {
  private WeatherSearchFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionNavigationWeatherSearchToNavigationWeatherForecast() {
    return new ActionOnlyNavDirections(R.id.action_navigation_weather_search_to_navigation_weather_forecast);
  }

  @NonNull
  public static NavDirections actionNavigationWeatherSearchToNavigationWeather() {
    return new ActionOnlyNavDirections(R.id.action_navigation_weather_search_to_navigation_weather);
  }
}
