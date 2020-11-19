package com.example.groupproject_g3.weather.fragments;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDaysItem {

    private final int mWeatherID;

    private final String mDay;

    private final String mDate;

    private final String mIcon;

    private final int mMin;

    private final int mMax;

    public WeatherDaysItem(int weatherId, String day, String date, String icon, int min, int max) {
        mWeatherID = weatherId;
        mDay = day;
        mDate = date;
        mIcon = icon;
        mMin = min;
        mMax = max;
    }

    public int getmWeatherID() { return mWeatherID; }

    public String getmDay() { return mDay; }

    public String getmDate() { return mDate; }

    public String getmIcon() { return mIcon; }

    public int getmMin() { return mMin; }

    public int getmMax() { return mMax; }

    /**
     * Static factory method to turn a properly formatted JSON String into a
     * WeatherDaysItem object.
     * @param cmAsJson the String to be parsed into a WeatherDaysItem Object.
     * @return a WeatherDaysItem Object with the details contained in the JSON String.
     * @throws JSONException when cmAsString cannot be parsed into a ChatMessage.
     */
    public static WeatherDaysItem createFromJsonString(final String cmAsJson) throws JSONException {
        final JSONObject msg = new JSONObject(cmAsJson);
        return new WeatherDaysItem(msg.getInt("id"),
                msg.getString("day"),
                msg.getString("date"),
                msg.getString("icon"),
                msg.getInt("min"),
                msg.getInt("max"));
    }

    /**
     * Provides equality solely based on weatherId.
     * @param other the other object to check for equality
     * @return true if other message ID matches this message ID, false otherwise
     */
    @Override
    public boolean equals(@Nullable Object other) {
        boolean result = false;
        if (other instanceof WeatherDaysItem) {
            result = mWeatherID == ((WeatherDaysItem) other).mWeatherID;
        }
        return result;
    }
}
