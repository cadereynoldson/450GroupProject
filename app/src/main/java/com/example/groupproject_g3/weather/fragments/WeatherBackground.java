package com.example.groupproject_g3.weather.fragments;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.example.groupproject_g3.R;

public class WeatherBackground {

    private View mView;
    private String mIcon;
    private String mWeather;
    private Resources res;
    private Drawable drawable;

    public WeatherBackground(View view, String theWeather, String theIcon, Resources theRes) {
        mView = view;
        mWeather = theWeather;
        mIcon = theIcon;
        res = theRes;
        background(mView, mWeather, mIcon, res);
    }

    @SuppressLint("ResourceAsColor")
    public void background(@NonNull View view, String weather, String icon, Resources res) {
        if (weather.matches("clear sky")) {
            if (icon.contains("01d")) {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_clear_sky, null);
//                binding.containerWeatherForecast.layoutCurrent.setBackgroundColor(R.color.primaryLightColor);
            } else {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_clear_sky_night, null);
            }
        } else if (weather.matches("few clouds")) {
            if (icon.contains("02d")) {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_few_clouds, null);
            } else {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_few_clouds_night, null);
            }
        } else if (weather.matches("scattered clouds")) {
            if (icon.contains("03d")) {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_scattered_clouds, null);
            } else {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_scattered_clouds_night, null);
            }
        } else if (weather.matches("broken clouds")) {
            if (icon.contains("04d")) {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_broken_clouds, null);
            } else {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_broken_clouds_night, null);
            }
        } else if (weather.matches("overcast clouds")) {
            if (icon.contains("04d")) {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_overcast_clouds, null);
            } else {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_overcast_clouds_night, null);
            }
        }
        view.setBackground(drawable);
    }

}
