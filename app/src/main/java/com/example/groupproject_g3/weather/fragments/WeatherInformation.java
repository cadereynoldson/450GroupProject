package com.example.groupproject_g3.weather.fragments;

import android.graphics.Bitmap;

import java.io.Serializable;

public class WeatherInformation implements Serializable {

    private String mDate;
    private String mTemperature;
    private String mTime;
    private String mWeather;
    private String mDay;
    private String mFeelsLike;
    private String mTempMin;
    private String mTempMax;
    private String mPressure;
    private String mHumidity;
    private String mPop;
    private String mLocation;
    private String mCountry;
    private String mLat;
    private String mLon;
    private String mSunrise;
    private String mSunset;
    private String mSpeed;
    private String mDirection;
    private String mIcon;
    private long mTimeZone;

    public static class Builder {
        private String mDate;
        private String mTemperature;
        private String mTime = "";
        private String mWeather = "";
        private String mDay = "";
        private String mFeelsLike = "";
        private String mTempMin = "";
        private String mTempMax = "";
        private String mPressure = "";
        private String mHumidity = "";
        private String mPop = "";
        private String mLocation = "";
        private String mCountry = "";
        private String mLat = "";
        private String mLon = "";
        private String mSunrise = "";
        private String mSunset = "";
        private String mSpeed = "";
        private String mDirection = "";
        private String mIcon;
        private long mTimeZone;


        public Builder(String theDate, String theTemperature) {
            mDate = theDate;
            mTemperature = theTemperature;
        }

        public Builder addTime(String theTime) {
            mTime = theTime;
            return this;
        }

        public Builder addWeather(String theWeather) {
            mWeather = theWeather;
            return this;
        }

        public Builder addDay(String theDay) {
            mDay = theDay;
            return this;
        }

        public Builder addFeelsLike(String theFeelsLike) {
            mFeelsLike = theFeelsLike;
            return this;
        }

        public Builder addTempMin(String theTempMin) {
            mTempMin = theTempMin;
            return this;
        }

        public Builder addTempMax(String theTempMax) {
            mTempMax = theTempMax;
            return this;
        }

        public Builder addPressure(String thePressure) {
            mPressure = thePressure;
            return this;
        }

        public Builder addHumidity(String theHumidity) {
            mHumidity = theHumidity;
            return this;
        }

        public Builder addPrecipitation(String thePop) {
            mPop = thePop;
            return this;
        }

        public Builder addLocation(String theLocation) {
            mLocation = theLocation;
            return this;
        }

        public Builder addCountry(String theCountry) {
            mCountry = theCountry;
            return this;
        }

        public Builder addLat(String theLat) {
            mLat = theLat;
            return this;
        }

        public Builder addLon(String theLon) {
            mLon = theLon;
            return this;
        }

        public Builder addSunrise(String theSunrise) {
            mSunrise = theSunrise;
            return this;
        }

        public Builder addSunset(String theSunset) {
            mSunset = theSunset;
            return this;
        }

        public Builder addSpeed(String theSpeed) {
            mSpeed = theSpeed;
            return this;
        }

        public Builder addDirection(String theDirection) {
            mDirection = theDirection;
            return this;
        }

        public Builder addIcon(String theIcon) {
            mIcon = theIcon;
            return this;
        }

        public Builder addTimeZone(long timezone) {
            mTimeZone = timezone;
            return this;
        }

        public WeatherInformation build() { return new WeatherInformation(this);}
    }

    private WeatherInformation(Builder builder) {
        mDate = builder.mDate;
        mTemperature = builder.mTemperature;
        mTime = builder.mTime;
        mWeather = builder.mWeather;
        mDay = builder.mDay;
        mFeelsLike = builder.mFeelsLike;
        mTempMin = builder.mTempMin;
        mTempMax = builder.mTempMax;
        mPressure = builder.mPressure;
        mHumidity = builder.mHumidity;
        mPop = builder.mPop;
        mLocation = builder.mLocation;
        mCountry = builder.mCountry;
        mLat = builder.mLat;
        mLon = builder.mLon;
        mSunrise = builder.mSunrise;
        mSunset = builder.mSunset;
        mSpeed = builder.mSpeed;
        mDirection = builder.mDirection;
        mIcon = builder.mIcon;
        mTimeZone = builder.mTimeZone;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmTemperature() {
        return mTemperature;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmWeather() {
        return mWeather;
    }

    public String getmDay() {
        return mDay;
    }

    public String getmFeelsLike() {
        return mFeelsLike;
    }

    public String getmTempMin() {
        return mTempMin;
    }

    public String getmTempMax() {
        return mTempMax;
    }

    public String getmPressure() {
        return mPressure;
    }

    public String getmHumidity() {
        return mHumidity;
    }

    public String getmPop() {
        return mPop;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmCountry() {
        return mCountry;
    }

    public String getmLat() {
        return mLat;
    }

    public String getmLon() {
        return mLon;
    }

    public String getmSunrise() { return mSunrise; }

    public String getmSunset() { return mSunset; }

    public String getmSpeed() { return mSpeed; }

    public String getmDirection() { return mDirection; }

    public String getmIcon() { return mIcon; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WeatherInformation))
            return false;
        else
            return (((WeatherInformation) o).mDate.equals(mDate) && ((WeatherInformation) o).mTime.equals(mTime));
    }

}
