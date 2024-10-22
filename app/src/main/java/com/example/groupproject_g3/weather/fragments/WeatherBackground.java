package com.example.groupproject_g3.weather.fragments;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.example.groupproject_g3.R;

public class WeatherBackground {

    /** View View to be used. */
    private View mView;

    /** String Weather Icon to be used. */
    private String mIcon;

    /** String Weather Description to be used. */
    private String mWeather;

    /** Resources folder Res to be used. */
    private Resources res;

    /** Drawable folder Drawable object to be used. */
    private Drawable drawable;

    /**
     * Constructor that takes in view, description, icon, and res to be assigned and used.
     * @param view The view fragment to be used.
     * @param theWeather The weather description to be used.
     * @param theIcon The weather icon to be used.
     * @param theRes The Resources folder that contains the backgrounds.
     */
    public WeatherBackground(View view, String theWeather, String theIcon, Resources theRes) {
        mView = view;
        mWeather = theWeather;
        mIcon = theIcon;
        res = theRes;
        background(mView, mWeather, mIcon, res);
    }

    /**
     * Method will check if description match and website contains the icon,
     * if all check pass, background will be set to a drawable.
     * @param view The view fragment to be used.
     * @param weather The weather description to be used.
     * @param icon The weather icon to be used.
     * @param res The Resources folder that contains the backgrounds.
     */
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
        } else if (weather.contains("thunderstorm") || weather.matches("squalls")) {
            if (icon.contains("11")) {
                if (weather.contains("rain") || weather.contains("drizzle")) {
                    drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_thunderstorm_rain, null);
                } else {
                    drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_thunderstorm, null);
                }
            }
        } else if (weather.contains("drizzle")) {
            if (icon.contains("09d")) {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_drizzle_rain, null);
            } else {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_drizzle_rain_night, null);
            }
        } else if (weather.contains("rain")) {
            if (icon.contains("10d")) {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_light_rain, null);
            } else if (weather.matches("freezing rain") || icon.contains("13")) {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_freezing_rain, null);
            } else if (weather.contains("shower")) {
                if (icon.contains("09")) {
                    drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_shower_rain, null);
                }
            } else {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_light_rain_night, null);
            }
        } else if (weather.contains("snow") || weather.contains("sleet") || weather.contains("Sleet")) {
            if (icon.contains("13d") || icon.contains("13n")) {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_snow, null);
            }
        } else if (weather.contains("mist") || weather.contains("Haze")) {
            if (icon.contains("50d")) {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_mist, null);
            } else {
                drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_mist_night, null);
            }
        } else if (weather.contains("fog") || weather.contains("Smoke")) {
            drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_fog, null);
        } else if (weather.contains("sand") || weather.contains("dust") || weather.contains("ash")) {
            drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_sand, null);
        } else if (weather.matches("tornado")) {
            drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_tornado, null);
        } else {
            drawable = ResourcesCompat.getDrawable(res, R.drawable.weather_default, null);
        }
        view.setBackground(drawable);
    }
}
