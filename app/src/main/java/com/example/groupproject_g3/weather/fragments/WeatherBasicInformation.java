package com.example.groupproject_g3.weather.fragments;

public class WeatherBasicInformation {
    /** The date of this current weather information. */
    private String date;

    /** The time this weather information was created. */
    private String time;

    /** The temperature of the current time. */
    private String temperature;

    /** The current weather (rainy, cloudy, sunny, etc.) at the current time. */
    private String weather;

    public WeatherBasicInformation(String theDate, String theTime, String theTemperature, String theWeather) {
        date = theDate;
        time = theTime;
        temperature = theTemperature;
        weather = theWeather;
    }

    public String getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWeather() {
        return weather;
    }

    public String getTime() {
        return time;
    }

    public String toString() {
        return date + " " + time + " " + temperature + " " + weather;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WeatherBasicInformation))
            return false;
        else
            return ((WeatherBasicInformation) o).date.equals(date); //TODO: Update
    }
}
