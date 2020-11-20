/**
 * Contains weather information data that will be handled.
 */

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

    /**
     * Instance of the required Weather information.
     * @param theDate
     * @param theTime
     * @param theTemperature
     * @param theWeather
     */
    public WeatherBasicInformation(String theDate, String theTime, String theTemperature, String theWeather) {
        date = theDate;
        time = theTime;
        temperature = theTemperature;
        weather = theWeather;
    }

    /**
     * Retreives the date data
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Retrieves the temperature data.
     * @return temperature
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * Retrieves the weather data in its entirety.
     * @return weather.
     */
    public String getWeather() {
        return weather;
    }

    /**
     * Retrieves the associated time.
     * @return time
     */
    public String getTime() {
        return time;
    }

    /**
     * A string representation of the date, time, temperature, and weather.
     * @return
     */
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
