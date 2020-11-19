package com.example.groupproject_g3.weather.fragments;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class WeatherHoursItem implements Serializable {

    private final int mWeatherID;

    private final int mHour;

    private final String mIcon;

    private final int mTemp;

    public WeatherHoursItem(int weatherId, int hour, String icon, int temp) {
        mWeatherID = weatherId;
        mHour = hour;
        mIcon = icon;
        mTemp = temp;
    }

    public int getmWeatherID() { return mWeatherID; }

    public int getmHour() { return mHour; }

    public String getmIcon() { return mIcon; }

    public int getmTemp() { return mTemp; }

    /**
     * Static factory method to turn a properly formatted JSON String into a
     * WeatherHoursItem object.
     * @param cmAsJson the String to be parsed into a WeatherHoursItem Object.
     * @return a WeatherHoursItem Object with the details contained in the JSON String.
     * @throws JSONException when cmAsString cannot be parsed into a ChatMessage.
     */
    public static WeatherHoursItem createFromJsonString(final String cmAsJson) throws JSONException {
        final JSONObject msg = new JSONObject(cmAsJson);
        return new WeatherHoursItem(msg.getInt("id"),
                msg.getInt("hour"),
                msg.getString("icon"),
                msg.getInt("temp"));
    }

    /**
     * Provides equality solely based on weatherId.
     * @param other the other object to check for equality
     * @return true if other message ID matches this message ID, false otherwise
     */
    @Override
    public boolean equals(@Nullable Object other) {
        boolean result = false;
        if (other instanceof WeatherHoursItem) {
            result = mWeatherID == ((WeatherHoursItem) other).mWeatherID;
        }
        return result;
    }
}
