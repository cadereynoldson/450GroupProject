// Generated by view binder compiler. Do not edit!
package com.example.groupproject_g3.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.example.groupproject_g3.R;
import com.google.android.material.chip.Chip;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentPageWeatherBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final RecyclerView RecycleViewDays;

  @NonNull
  public final RecyclerView RecycleViewHours;

  @NonNull
  public final CardView cardWeather24hours;

  @NonNull
  public final CardView cardWeatherDays;

  @NonNull
  public final Chip chipWeatherLocation;

  @NonNull
  public final LayoutWeatherForecastBinding containerWeatherForecast;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final RelativeLayout weatherRoot;

  private FragmentPageWeatherBinding(@NonNull RelativeLayout rootView,
      @NonNull RecyclerView RecycleViewDays, @NonNull RecyclerView RecycleViewHours,
      @NonNull CardView cardWeather24hours, @NonNull CardView cardWeatherDays,
      @NonNull Chip chipWeatherLocation,
      @NonNull LayoutWeatherForecastBinding containerWeatherForecast,
      @NonNull ProgressBar progressBar, @NonNull RelativeLayout weatherRoot) {
    this.rootView = rootView;
    this.RecycleViewDays = RecycleViewDays;
    this.RecycleViewHours = RecycleViewHours;
    this.cardWeather24hours = cardWeather24hours;
    this.cardWeatherDays = cardWeatherDays;
    this.chipWeatherLocation = chipWeatherLocation;
    this.containerWeatherForecast = containerWeatherForecast;
    this.progressBar = progressBar;
    this.weatherRoot = weatherRoot;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPageWeatherBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPageWeatherBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_page_weather, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPageWeatherBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.RecycleView_days;
      RecyclerView RecycleViewDays = rootView.findViewById(id);
      if (RecycleViewDays == null) {
        break missingId;
      }

      id = R.id.RecycleView_hours;
      RecyclerView RecycleViewHours = rootView.findViewById(id);
      if (RecycleViewHours == null) {
        break missingId;
      }

      id = R.id.card_weather_24hours;
      CardView cardWeather24hours = rootView.findViewById(id);
      if (cardWeather24hours == null) {
        break missingId;
      }

      id = R.id.card_weather_days;
      CardView cardWeatherDays = rootView.findViewById(id);
      if (cardWeatherDays == null) {
        break missingId;
      }

      id = R.id.chip_weather_location;
      Chip chipWeatherLocation = rootView.findViewById(id);
      if (chipWeatherLocation == null) {
        break missingId;
      }

      id = R.id.container_weather_forecast;
      View containerWeatherForecast = rootView.findViewById(id);
      if (containerWeatherForecast == null) {
        break missingId;
      }
      LayoutWeatherForecastBinding binding_containerWeatherForecast = LayoutWeatherForecastBinding.bind(containerWeatherForecast);

      id = R.id.progressBar;
      ProgressBar progressBar = rootView.findViewById(id);
      if (progressBar == null) {
        break missingId;
      }

      RelativeLayout weatherRoot = (RelativeLayout) rootView;

      return new FragmentPageWeatherBinding((RelativeLayout) rootView, RecycleViewDays,
          RecycleViewHours, cardWeather24hours, cardWeatherDays, chipWeatherLocation,
          binding_containerWeatherForecast, progressBar, weatherRoot);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
